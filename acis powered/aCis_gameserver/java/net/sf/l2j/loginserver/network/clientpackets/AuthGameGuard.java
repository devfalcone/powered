package net.sf.l2j.loginserver.network.clientpackets;

import net.sf.l2j.loginserver.L2LoginClient.LoginClientState;
import net.sf.l2j.loginserver.network.serverpackets.GGAuth;
import net.sf.l2j.loginserver.network.serverpackets.LoginFail.LoginFailReason;

/**
 * @author -Wooden- Format: ddddd
 */
public class AuthGameGuard extends L2LoginClientPacket
{
	private int _sessionId;
	private int _data1;
	private int _data2;
	private int _data3;
	private int _data4;
	
	public int getSessionId()
	{
		return _sessionId;
	}
	
	public int getData1()
	{
		return _data1;
	}
	
	public int getData2()
	{
		return _data2;
	}
	
	public int getData3()
	{
		return _data3;
	}
	
	public int getData4()
	{
		return _data4;
	}
	
	/**
	 * @see net.sf.l2j.loginserver.network.clientpackets.L2LoginClientPacket readImpl()
	 */
	@Override
	protected boolean readImpl()
	{
		if (super._buf.remaining() >= 20)
		{
			_sessionId = readD();
			_data1 = readD();
			_data2 = readD();
			_data3 = readD();
			_data4 = readD();
			return true;
		}
		return false;
	}
	
	@Override
	public void run()
	{
		if (_sessionId == getClient().getSessionId())
		{
			getClient().setState(LoginClientState.AUTHED_GG);
			getClient().sendPacket(new GGAuth(getClient().getSessionId()));
		}
		else
			getClient().close(LoginFailReason.REASON_ACCESS_FAILED);
	}
}