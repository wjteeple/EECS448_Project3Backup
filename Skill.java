public class Skill
{
	//Member Variables
	private String m_name;
	private String m_description;
	private boolean m_targetsEnemy;
	private int m_id;
	private double m_accuracy;

	static int m_numOfSkills=8;

	//static int m_numOfSkills=9;



	public static int getNumOfSkillsTotal()
	{
		return m_numOfSkills;
	}
	//Item creator
	public static Skill[] getSkills()
	{
		Skill[] skillSet = new Skill[8];
		for(int i=0; i<skillSet.length; i++)
		{
			skillSet[i]=new Skill(i);
		}
		return skillSet;
	}

	//Constructor
	public Skill(int skillNum)
	{
		m_accuracy=.8;
		if(skillNum==0)
		{
			m_name="Basic Attack";
			m_description="A basic attack";
			m_targetsEnemy=true;
			m_id=0;

		}
		else if(skillNum==1)
		{
			m_name="Power Attack";
			m_description="A very strong attack that is not very accurate";
			m_targetsEnemy=true;

			m_accuracy=.7;
		}
		else if(skillNum==2)
		{
			m_name="Threading Needle";
			m_description="A very accurate attack whose attack power is reduced";
			m_targetsEnemy=true;

		}
		else if(skillNum==3)
		{
			m_name="Shell";
			m_description="Increase defense or the remainder of the battle";
			m_targetsEnemy=false;
			m_accuracy=1;
		}
		else if(skillNum==4)
		{
			m_name="Heal";
			m_description="Heals the caster for half their attack value";
			m_targetsEnemy=false;
		}
		else if(skillNum==5)
		{
			m_name="Strengthen";
			m_description="Increases the user's attack power for the duration of the battle";
			m_targetsEnemy=false;
		}
		else if(skillNum==6)
		{
			m_name="Penetrating Strike";
			m_description="Deals a reduced amount of damage but ignores a targets armor";
			m_targetsEnemy=true;
		}
		else if(skillNum==7)
		{
			m_name="Blood Ritual";
			m_description="Attack yourself for an increase in attack damage";
			m_targetsEnemy=false;
		}
		else if(skillNum==8)
		{
			m_name="Berserker Rage";
			m_description="When you are below half health, increase your attack power. Does nothing if above half health";
			m_targetsEnemy=false;

		}
		m_id=skillNum;
	}

	public void Execute(Actor User, Actor Target)
	{
		//TODO incorporate accuracy into calculation using random
		if(this.m_id==0)
		{
			if((User.getAttackFighter()-(.5*Target.getDefenseFighter())>0))
			{
				Target.reduceHp((int) (User.getAttackFighter()-(.5*Target.getDefenseFighter())));
			}
			else
			{
				Target.reduceHp(1);
			}
		}
		else if(this.m_id==1)
		{
			if((1.5)*User.getAttackFighter()-.5*Target.getDefenseFighter()>0)
			{
				Target.reduceHp((int)((1.5)*User.getAttackFighter()-.5*Target.getDefenseFighter()));
			}
			else
			{
				Target.reduceHp(1);
			}
		}
		else if(this.m_id==2)
		{
			if(.8*User.getAttackFighter()-.5*Target.getDefenseFighter()>0)
			{
				Target.reduceHp((int)(.8*User.getAttackFighter()-.5*Target.getDefenseFighter()));
			}
			else
			{
				Target.reduceHp(1);
			}
		}
		else if(this.m_id==3)
		{
			User.setDefenseModifier(1.2);
		}
		else if(this.m_id==4)
		{
			if(User.getCurHp()+(.5)*User.getAttackFighter()>User.getMaxHp())//The case where healing heals more than possible
			{
				User.setCurHp(User.getMaxHp());
			}
			else
			{
				User.setCurHp((int)(User.getCurHp()+User.getAttackFighter()*.5));
			}
		}
		else if(this.m_id==5)
		{
			User.setAttackModifier(1.2);
		}
		else if(this.m_id==6)
		{
			Target.reduceHp((int)(.5*User.getAttackFighter()));//calculation doesn't regard the defense of the target
		}
		else if(this.m_id==7)
		{
			User.reduceHp((int)(User.getAttackFighter()));//attacking self is intentional, its payment for a boost in attack power
			User.setAttackModifier(1.35);
		}
		else if(this.m_id==8)
		{
			if(User.getCurHp()<=(.5)*User.getMaxHp())
			{
				User.setAttackModifier(1.3);
			}
		}
	}



	//Getters and Setters
	public String getName()
	{
		return m_name;
	}
	public void setName(String name)
	{
		m_name=name;
	}
	public String getDescription()
	{
		return m_description;
	}
	public void setDescription(String description)
	{
		m_description=description;
	}
	public boolean getTargetsEnemy()
	{
		return m_targetsEnemy;
	}
	public void setTargetsEnemy(boolean targetsEnemy)
	{
		m_targetsEnemy=targetsEnemy;
	}
	public int getId()
	{
		return m_id;
	}
	public void setId(int id)
	{
		m_id=id;
	}
	public double getAccuracy()
	{
		return m_accuracy;
	}
	public void setAccuracy(double accuracy)
	{
		if(accuracy > 1)
		{
			m_accuracy=1;
		}
		else if(accuracy<0)
		{
			m_accuracy=0;
		}
		else
		{
			m_accuracy=accuracy;
		}
	}
}
