public class Actor 
{
	Item[] itemSet = Item.getAllItems();
	private String m_name;
	private int m_level;
	private int m_maxHp;//max Hit Points
	private int m_curHp;//current Hit Points
	private int m_atk;
	private double m_atkModifier;
	private int m_def;
	private double m_defModifier;
	private Item m_equippedSword;
	private Item m_equippedShield;
	private Item m_equippedArmor;
	private Item m_equippedHelmet;
	private Item m_equippedBoots;
	private Item m_equippedGauntlets;
	protected boolean[] m_skillSet;
	//Constructors
	public Actor()//default constructor
	{
		Item[] itemArray = Item.getAllItems();
		m_level=1;
		m_maxHp=100;
		m_curHp=100;
		m_atk=10;
		m_atkModifier=1;
		m_def=10;
		m_defModifier=1;

		equipSword(itemArray[8]);
		equipShield(itemArray[9]);
		equipArmor(itemArray[10]);
		equipHelmet(itemArray[11]);
		equipGauntlets(itemArray[12]);
		equipBoots(itemArray[13]);
		setAttackModifier(1);
		setDefenseModifier(1);

		equipSword(itemSet[8]);
		equipShield(itemSet[9]);
		equipArmor(itemSet[10]);
		equipHelmet(itemSet[11]);
		equipBoots(itemSet[13]);
		equipGauntlets(itemSet[12]);

		m_skillSet=new boolean[Skill.getNumOfSkillsTotal()];
		for(int i=0; i<Skill.getNumOfSkillsTotal();i++)
		{
			m_skillSet[i]=false;
		}
		m_skillSet[0]=true;//sets the basic attack skill to true to ensure that it is avaliable to all
	}


	//Setters and Getters

	public boolean[] getSkillset()
	{
		return m_skillSet;
	}

	public String getName()
	{
		return m_name;
	}
	public void setName(String name)
	{
		m_name=name;
	}
	public int getLevel()
	{
		return m_level;
	}
	public void setLevel(int level)
	{
		m_level=level;
	}
	public int getMaxHp()
	{
		return m_maxHp;
	}
	public void setMaxHp(int maxHP)
	{
		m_maxHp=maxHP;
	}
	public int getCurHp()
	{
		return m_curHp;
	}
	public void setCurHp(int curHp)
	{
		m_curHp=curHp;
	}
	public int getAtk()
	{
		return m_atk;
	}
	public void setAtk(int Atk)
	{
		m_atk=Atk;
	}
	public double getAtkModifier()
	{
		return m_atkModifier;
	}
	public void setAttackModifier(double AttackModifier)
	{
		if(AttackModifier>=5)
		{
			m_atkModifier=5;
		}
		else if(AttackModifier<0)
		{
			m_atkModifier=0;
		}
		else
		{
			m_atkModifier=AttackModifier;
		}
	}
	public int getDef()
	{
		return m_def;
	}
	public void setDef(int Def)
	{
		m_def=Def;
	}
	public double getDefModifier()
	{
		return m_defModifier;
	}
	public void setDefenseModifier(double DefenseModifier)
	{
		if(DefenseModifier>=5)
		{
			m_defModifier=5;
		}
		else if(DefenseModifier<0)
		{
			m_defModifier=0;
		}
		else
		{
			m_defModifier=DefenseModifier;
		}
	}
	public Item getEquippedSword()
	{
		return m_equippedSword;
	}
	public void equipSword(Item sword)
	{
		if(sword.getType()==1)
		{
			m_equippedSword=sword;
		}
		else
		{
			System.out.println("Cannot equip that in the sword slot!");
		}
	}
	public Item getEquippedShield()
	{
		return m_equippedShield;
	}
	public void equipShield(Item shield)
	{
		if(shield.getType()==2)
		{
			m_equippedShield=shield;
		}
		else
		{
			System.out.println("Cannot equip that in the Sheild slot!");
		}
	}
	public Item getEquippedArmor()
	{
		return m_equippedArmor;
	}
	public void equipArmor(Item armor)
	{
		if(armor.getType()==3)
		{
			m_equippedArmor=armor;
		}
		else
		{
			System.out.println("Cannot equip that in the Armor Slot!");
		}
	}
	public Item getEquippedHelmet()
	{
		return m_equippedHelmet;
	}
	public void equipHelmet(Item helmet)
	{
		if(helmet.getType()==4)
		{
			m_equippedHelmet=helmet;
		}
		else
		{
			System.out.println("Cannot equip that in the Helmet slot!");
		}
	}
	public Item getEquippedGauntlets()
	{
		return m_equippedGauntlets;
	}
	public void equipGauntlets(Item gauntlets)
	{
		if(gauntlets.getType()==5)
		{
			m_equippedGauntlets=gauntlets;
		}
		else
		{
			System.out.println("Cannot equip that in the Gauntlet slot!");
		}
	}
	public Item getEquippedBoots()
	{
		return m_equippedBoots;
	}
	public void equipBoots(Item boots)
	{
		if(boots.getType()==6)
		{
			m_equippedBoots=boots;
		}
		else
		{
			System.out.println("Cannot equip that in the Boots slot!");
		}
	}

	//Battle Methods
	public boolean reduceHp(int damage)//returns bool if fatal
	{
		m_curHp=m_curHp-damage;
		if(m_curHp>0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean basicAttack(Actor opponent)
	{
		return opponent.reduceHp(this.getAtk());
	}

	//getters and setters for battle that have the alteration from the items in addition to the basics
	public int getAttackFighter()
	{
		return ((int)((getAtk()+m_equippedSword.getBonusAtk()+m_equippedShield.getBonusAtk()+m_equippedHelmet.getBonusAtk()+m_equippedBoots.getBonusAtk() + m_equippedGauntlets.getBonusAtk())*getAtkModifier()));
	}
	public int getDefenseFighter()
	{
		return ((int)((getDef()+m_equippedSword.getBonusDef()+m_equippedShield.getBonusDef()+m_equippedHelmet.getBonusDef()+m_equippedBoots.getBonusDef()+m_equippedGauntlets.getBonusDef())*getDefModifier()));
	}
}
