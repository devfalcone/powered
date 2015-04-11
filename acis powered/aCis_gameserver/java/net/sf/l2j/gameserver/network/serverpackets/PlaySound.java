package net.sf.l2j.gameserver.network.serverpackets;

public class PlaySound extends L2GameServerPacket
{
	private final String _soundFile;
	private final int _unknown1, _unknown3, _unknown4, _unknown5, _unknown6, _unknown7, _unknown8;
	
	public PlaySound(String soundFile)
	{
		_unknown1 = 0;
		_soundFile = soundFile;
		_unknown3 = 0;
		_unknown4 = 0;
		_unknown5 = 0;
		_unknown6 = 0;
		_unknown7 = 0;
		_unknown8 = 0;
	}
	
	public PlaySound(int unknown1, String soundFile, int unknown3, int unknown4, int unknown5, int unknown6, int unknown7)
	{
		_unknown1 = unknown1;
		_soundFile = soundFile;
		_unknown3 = unknown3;
		_unknown4 = unknown4;
		_unknown5 = unknown5;
		_unknown6 = unknown6;
		_unknown7 = unknown7;
		_unknown8 = 0;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x98);
		writeD(_unknown1); // 0 for quest and ship;
		writeS(_soundFile);
		writeD(_unknown3); // 0 for quest; 1 for ship;
		writeD(_unknown4); // 0 for quest; objectId of ship
		writeD(_unknown5); // x
		writeD(_unknown6); // y
		writeD(_unknown7); // z
		writeD(_unknown8);
	}
}