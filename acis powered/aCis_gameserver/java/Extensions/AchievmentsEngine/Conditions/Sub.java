package Extensions.AchievmentsEngine.Conditions;

import Extensions.AchievmentsEngine.Base.Condition;

import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class Sub extends Condition
{
	public Sub(Object value)
	{
		super(value);
		setName("Subclass Count");
	}
	
	@Override
	public boolean meetConditionRequirements(L2PcInstance player)
	{
		if (getValue() == null)
			return false;
		
		int val = Integer.parseInt(getValue().toString());
		
		if (player.getSubClasses().size() >= val)
			return true;
		
		return false;
	}
}