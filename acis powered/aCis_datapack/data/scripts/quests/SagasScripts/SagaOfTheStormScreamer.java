package quests.SagasScripts;

/**
 * @author Emperorc
 */
public class SagaOfTheStormScreamer extends SagasSuperClass
{
	public static String qn1 = "Q090_SagaOfTheStormScreamer";
	public static int qnu = 90;
	public static String qna = "Saga of the Storm Screamer";
	
	public SagaOfTheStormScreamer()
	{
		super(qnu, qn1, qna);
		
		NPC = new int[]
		{
			30175,
			31627,
			31287,
			31287,
			31598,
			31646,
			31649,
			31652,
			31654,
			31655,
			31659,
			31287
		};
		
		Items = new int[]
		{
			7080,
			7531,
			7081,
			7505,
			7288,
			7319,
			7350,
			7381,
			7412,
			7443,
			7084,
			0
		};
		
		Mob = new int[]
		{
			27252,
			27239,
			27256
		};
		
		qn = qn1;
		classid = 110;
		prevclass = 0x28;
		
		X = new int[]
		{
			161719,
			124376,
			124355
		};
		
		Y = new int[]
		{
			-92823,
			82127,
			82155
		};
		
		Z = new int[]
		{
			-1893,
			-2796,
			-2803
		};
		
		registerNPCs();
	}
}