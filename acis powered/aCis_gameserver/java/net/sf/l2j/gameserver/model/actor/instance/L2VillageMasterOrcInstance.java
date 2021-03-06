package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.base.PlayerClass;
import net.sf.l2j.gameserver.model.base.Race;

public final class L2VillageMasterOrcInstance extends L2VillageMasterInstance
{
	public L2VillageMasterOrcInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	protected final boolean checkVillageMasterRace(PlayerClass pclass)
	{
		if (pclass == null)
			return false;
		
		return pclass.isOfRace(Race.Orc);
	}
}