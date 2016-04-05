import java.util.Scanner;
public class PlayerActor extends Actor
{
	static Scanner myScanner = new Scanner(System.in);
	static int select;
	Item[] itemSet = Item.getAllItems();
	//private member variables
	private int m_exp;
	private int m_gold;
	private int[] m_inventory;

	//Constructor
	public PlayerActor()
	{
		this.setLevel(1);
		m_exp=0;
		m_inventory=new int[Item.getNumTypesOfItem()];
		for(int i=0; i<Item.getNumTypesOfItem();i++)
		{
			m_inventory[i]=0;
		}
		equipSword(itemSet[8]);
		equipShield(itemSet[9]);
		equipArmor(itemSet[10]);
		equipHelmet(itemSet[11]);
		equipBoots(itemSet[13]);
		equipGauntlets(itemSet[12]);
	}

	//getters and setters TODO write JavaDocs for methods
	public int getExp()
	{
		return m_exp;
	}
	public void setExp(int exp)
	{
		m_exp=exp;
	}
	public int getGold()
	{
		return m_gold;
	}
	public void setGold(int gold)
	{
		m_gold=gold;
	}
	public void addGold(int gold)
	{
		m_gold=m_gold+gold;
	}
	public boolean buyItem(int index)//return true if buy is possible
	{
		if(m_gold>=itemSet[index].getValue())
		{
			m_gold=m_gold-itemSet[index].getValue();
			m_inventory[index]++;
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean canBuyItem(int cost)
	{
		if(m_gold>=cost)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean buyItems(int index, int quantity)
	{
		if(m_gold>=itemSet[index].getValue()*quantity)
		{
			m_gold=m_gold-(itemSet[index].getValue()*quantity);
			m_inventory[index]+=quantity;
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean canBuyItems(int cost, int quantity)
	{
		if(m_gold>=cost*quantity)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public int[] getInventory()
	{
		return m_inventory;
	}
	public void setInventory(int[] inventory)
	{
		m_inventory=inventory;
	}

	//Methods
	public void addExp(int addedExp)
	{
		m_exp+=addedExp;
		while(m_exp>100*this.getLevel())
		{
			levelUp(m_exp-100*this.getLevel());
		}
	}
	public void levelUp(int exp)
	{
		m_exp-=100*this.getLevel();
		setLevel(getLevel()+1);
		setMaxHp(getMaxHp()+10);
		setAtk(getAtk()+1);
		setDef(getDef()+1);
		//TODO implement choice for additional level up reward
	}
	public void menu()
	{
		System.out.println("1) Access Inventory\n2) Return");
		select=myScanner.nextInt();
		if(select==1)
		{
			accessInventory();
		}
		else if(select==2)
		{
			return;
		}
		else
		{
			System.out.println("Sorry, we didn't understand your input");
		}
	}
	public void accessInventory()
	{
		Item[] items = Item.getAllItems();
		System.out.println("Here's the stuff in your inventory");
		boolean quit=false;
		while(!quit)
		{
			System.out.println("Equipped Sword:  " + this.getEquippedSword().getName());
			System.out.println("Equipped Shield: " + this.getEquippedShield().getName());
			System.out.println("Equipped Armor: " + this.getEquippedArmor().getName());
			System.out.println("Equipped Helmet: " +  this.getEquippedHelmet().getName());
			System.out.println("Equipped Gauntlets: " + this.getEquippedGauntlets().getName());
			System.out.println("Equipped Boots: "+ this.getEquippedBoots().getName());
			System.out.println("1) Equip a Sword");
			System.out.println("2) Equip a Shield");
			System.out.println("3) Equip Armor");
			System.out.println("4) Equip Helmet");
			System.out.println("5) Equip Gauntlets");
			System.out.println("6) Equip Boots");
			System.out.println("7) Look at inventory");
			System.out.println("8) Return");
			select=myScanner.nextInt();
			if(select>=0&&select<7)
			{
				equipMenu(select);
			}
			else if(select==7)
			{
				displayInventory();
			}
			else if(select==8)
			{
				return;
			}
		}
	}
	public void displayInventory()
	{
		for(int i=0; i<itemSet.length;i++)
		{
			System.out.println("You have " + getInventory()[i] +" " + itemSet[i].getName() + " In your inventory");
		}
	}
	public void equipMenu(int type)//TODO finish equip method
	{
		Item[] items = Item.getAllItems();
		if(type==1)
		{
			System.out.println("Which Sword do you want to equip?");
		}
		else if(type==2)
		{
			System.out.println("Which Shield do you want to equip?");
		}
		else if(type==3)
		{
			System.out.println("Which Armor set do you want to equip?");
		}
		else if(type==4)
		{
			System.out.println("Which Helmet do you want to equip?");
		}
		else if(type==5)
		{
			System.out.println("Which Gauntlets do you want to equip?");
		}
		else if(type==6)
		{
			System.out.println("Which Boots do you want to equip?");
		}

		//initializes array to hold choices
		int[] indexRepresentedByChoice= new int[itemSet.length];
		for(int i=0;i<itemSet.length;i++)
		{
			indexRepresentedByChoice[i]=-1;//the value -1 represents not having that as a possible choice
		}
		int choices=0;
		for(int i=0; i<items.length;i++)
		{
			if(m_inventory[i]>0&&items[i].getType()==type)
			{
				System.out.println((choices+1) + ") " + items[i].getName() + " Attack Bonus: "+ items[i].getBonusAtk() + " Defense bonus: " + items[i].getBonusDef());
				indexRepresentedByChoice[choices+1]=i;
				choices++;
			}
		}
		System.out.println((choices+1) + ") Leave");

		//get input from user
		select=myScanner.nextInt();
		System.out.println("choices = " + choices);//debugging

		//make decision based on input
		if(select<1||select>choices)
		{
			System.out.println("Sorry, we didn't understand your input");
		}
		else if((select>1)&&(select<(choices+1)))
		{
			if(type==1)
			{
				this.equipSword(items[indexRepresentedByChoice[select]]);
				System.out.println("Equipped Sword successfully"); //debugging
			}
			else if(type==2)
			{
				this.equipShield(items[indexRepresentedByChoice[select]]);
			}
			else if(type==3)
			{
				this.equipArmor(items[indexRepresentedByChoice[select]]);
			}
			else if(type==4)
			{
				this.equipHelmet(items[indexRepresentedByChoice[select]]);
			}
			else if(type==5)
			{
				this.equipGauntlets(items[indexRepresentedByChoice[select]]);
			}
			else if(type==6)
			{
				this.equipBoots(items[indexRepresentedByChoice[select]]);
			}
		}
		else if(select==(choices+1))
		{
			return;
		}
		else
		{
			System.out.println("Sorry, we did not understand you input, please try again");
		}
	}
}
