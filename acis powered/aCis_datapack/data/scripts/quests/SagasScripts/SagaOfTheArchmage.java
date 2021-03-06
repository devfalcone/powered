package quests.SagasScripts;

/**
 * @author Emperorc
 */
public class SagaOfTheArchmage extends SagasSuperClass
{
	public static String qn1 = "Q088_SagaOfTheArchmage";
	public static int qnu = 88;
	public static String qna = "Saga of the Archmage";
	
	public SagaOfTheArchmage()
	{
		super(qnu, qn1, qna);
		
		NPC = new int[]
		{
			30176,
			31627,
			31282,
			31282,
			31590,
			31646,
			31647,
			31650,
			31654,
			31655,
			31657,
			31282
		};
		
		Items = new int[]
		{
			7080,
			7529,
			7081,
			7503,
			7286,
			7317,
			7348,
			7379,
			7410,
			7441,
			7082,
			0
		};
		
		Mob = new int[]
		{
			27250,
			27237,
			27254
		};
		
		qn = qn1;
		classid = 94;
		prevclass = 0x0c;
		
		X = new int[]
		{
			191046,
			46066,
			46087
		};
		
		Y = new int[]
		{
			-40640,
			-36396,
			-36372
		};
		
		Z = new int[]
		{
			-3042,
			-1685,
			-1685
		};
		
		registerNPCs();
	}
}