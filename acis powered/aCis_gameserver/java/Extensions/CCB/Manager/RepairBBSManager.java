package Extensions.CCB.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.Manager.BaseBBSManager;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class RepairBBSManager extends BaseBBSManager
{
	private final String HTM_PATH = "data/html/CommunityBoard/Custom/";
	
	@Override
	public void parseCmd(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("_bbsrepair"))
		{
			String filename = "";
			
			String repairChar = "";
			
			try
			{
				String val = command.substring(10);
				StringTokenizer st = new StringTokenizer(val);
				
				if (st.countTokens() == 1)
				{
					repairChar = st.nextToken();
				}
			}
			catch (Exception e)
			{
				repairChar = null;
			}
			
			if (repairChar == null)
			{
				activeChar.sendMessage("You have to choice character name to repair!");
			}
			else
			{
				if (checkAcc(activeChar, repairChar))
				{
					if (checkChar(activeChar, repairChar))
					{
						filename = HTM_PATH + "repair1.htm";
					}
					else if (checkJail(activeChar, repairChar))
					{
						filename = HTM_PATH + "repair2.htm";
					}
					else
					{
						repairBadCharacter(repairChar);
						filename = HTM_PATH + "repair3.htm";
					}
				}
				else
				{
					filename = HTM_PATH + "repair4.htm";
				}
			}
			
			String content = HtmCache.getInstance().getHtm(filename);
			content = content.replaceAll("%acc_chars%", getCharList(activeChar));
			separateAndSend(content, activeChar);
		}
	}
	
	private static boolean checkAcc(L2PcInstance activeChar, String repairChar)
	{
		boolean result = false;
		String repCharAcc = "";
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
	    {
			PreparedStatement statement = con.prepareStatement("SELECT account_name FROM characters WHERE char_name=?");
			statement.setString(1, repairChar);
			ResultSet rset = statement.executeQuery();
			if (rset.next())
			{
				repCharAcc = rset.getString(1);
			}
			rset.close();
			statement.close();
			
		}
		catch (SQLException e)
		{
			return result;
		}
		if (activeChar.getAccountName().compareTo(repCharAcc) == 0)
			result = true;
		return result;
	}
	
	private static boolean checkJail(L2PcInstance activeChar, String repairChar)
	{
		boolean result = false;
		int repCharJail = 0;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
	    {
			PreparedStatement statement = con.prepareStatement("SELECT punish_level FROM characters WHERE char_name=?");
			statement.setString(1, repairChar);
			ResultSet rset = statement.executeQuery();
			if (rset.next())
			{
				repCharJail = rset.getInt(1);
			}
			rset.close();
			statement.close();
			
		}
		catch (SQLException e)
		{
			return result;
		}
		if (repCharJail != 0)
			result = true;
		return result;
	}
	
	private static boolean checkChar(L2PcInstance activeChar, String repairChar)
	{
		boolean result = false;
		if (activeChar.getName().compareTo(repairChar) == 0)
			result = true;
		return result;
	}
	
	private static void repairBadCharacter(String charName)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
	    {
			PreparedStatement statement;
			statement = con.prepareStatement("SELECT obj_Id FROM characters WHERE char_name=?");
			statement.setString(1, charName);
			ResultSet rset = statement.executeQuery();
			
			int objId = 0;
			if (rset.next())
			{
				objId = rset.getInt(1);
			}
			rset.close();
			statement.close();
			if (objId == 0)
			{
				con.close();
				return;
			}
			statement = con.prepareStatement("UPDATE characters SET x=17867, y=170259, z=-3503 WHERE obj_Id=?");
			statement.setInt(1, objId);
			statement.execute();
			statement.close();
			statement = con.prepareStatement("DELETE FROM character_shortcuts WHERE char_obj_id=?");
			statement.setInt(1, objId);
			statement.execute();
			statement.close();
			statement = con.prepareStatement("UPDATE items SET loc=\"WAREHOUSE\" WHERE owner_id=? AND loc=\"PAPERDOLL\"");
			statement.setInt(1, objId);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			System.err.println("GameServer: could not repair character:" + e);
		}
	}
	
	/**
	 * @param activeChar
	 * @return
	 */
	private static String getCharList(L2PcInstance activeChar)
	{
		String result = "";
		String repCharAcc = activeChar.getAccountName();
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
	    {
			PreparedStatement statement = con.prepareStatement("SELECT char_name FROM characters WHERE account_name=?");
			statement.setString(1, repCharAcc);
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				if (activeChar.getName().compareTo(rset.getString(1)) != 0)
					result += rset.getString(1) + ";";
			}
			
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			return result;
		}
		return result;
	}
	
	@Override
	public void parseWrite(String ar1, String ar2, String ar3, String ar4, String ar5, L2PcInstance activeChar)
	{
		
	}
	
	public static RepairBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RepairBBSManager INSTANCE = new RepairBBSManager();
	}
}