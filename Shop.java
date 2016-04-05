package ver0;
import java.util.Scanner;
public class Shop 
{
	static Scanner myScanner = new Scanner(System.in);
	static int select;
	int[] m_inventory=new int[Item.getNumTypesOfItem()];
	
	
	public Shop(int whichShop)//used in case we make more than one shop
	{
		for(int i=0;i<Item.getNumTypesOfItem();i++)
		{
			m_inventory[i]=0;
		}
		if(whichShop==1)
		{
			for(int i=1;i<=6;i++)
			{
				m_inventory[i]=1;
			}
			m_inventory[7]=10;
		}
	}
	public void displayMenu(PlayerActor character)
	{
		Item[] itemSet = Item.getAllItems();
		boolean exit=false;
		while(!exit)
		{
			System.out.println("Welcome to our shop! feel free to buy stuff! We'd like that!\n1) Buy\n2) Sell\n3) Leave");
			select=myScanner.nextInt();
			if(select==1)
			{
				displayBuyMenu(character);
			}
			else if(select==2)
			{
				System.out.println("Due to developer laziness, it has been decided that you won't be able to buy anyting until the next Iteration!");//TODO make sell methods
			}
			else if(select==3)
			{
				System.out.println("Thank you for your custom, please come again!");
				return;
			}
			else
			{
				System.out.println("Sorry we didn't understand your input, please enter a 1 a 2 or a 3");
			}
		}	
	}
	public void displayBuyMenu(PlayerActor character)
	{
		Item[] itemSet = Item.getAllItems();
		boolean exit=false;
		while(!exit)
		{
			System.out.println("Please select what you would like to buy");
			int[] indexRepresentedByChoice= new int[itemSet.length];
			for(int i=0;i<itemSet.length;i++)
			{
				indexRepresentedByChoice[i]=-1;//the value -1 represents not having that as a possible choice
			}
			int choice=1;//this is the choice incrementer, that gets incremented each time we show a new option to the customer
			for(int i=0;i<Item.getNumTypesOfItem();i++)
			{
				if(m_inventory[i]>0)
				{
					System.out.println(choice + ") " + itemSet[i].getName() + " costs: " + itemSet[i].getValue());
					indexRepresentedByChoice[choice]=i;
					choice++;
				}
			}
			select=myScanner.nextInt();
			if(select<1||select>choice)
			{
				System.out.println("Sorry we cant understand you input, please enter one of the numbers provided");
			}
			else if(indexRepresentedByChoice[select]!=-1)
			{
				if(m_inventory[indexRepresentedByChoice[select]]<1)
				{
					System.out.println("Sorry, we don't have any of those in stock");
				}
				else if(m_inventory[indexRepresentedByChoice[select]]==1)
				{
					purchase(indexRepresentedByChoice[select],character);
				}
				else if(m_inventory[indexRepresentedByChoice[select]]>1)
				{
					System.out.println("How many " + itemSet[indexRepresentedByChoice[select]].getName() +" would you like to buy?");
					int quantity=myScanner.nextInt();
					{
						if(quantity<0)
						{
							System.out.println("Sorry, we cant give you a negative number, you can sell to us though!");
						}
						else if(quantity==1)
						{
							purchase(indexRepresentedByChoice[select],character);
						}
						else if(quantity>m_inventory[indexRepresentedByChoice[select]])
						{
							System.out.println("Sorry, we don't have that many!");
						}
						else if(quantity<=m_inventory[indexRepresentedByChoice[select]])
						{
							purchaseMultiple(indexRepresentedByChoice[select],quantity,character);
						}
						else
						{
							System.out.println("Sorry we don't undersand your input, please try again!");
						}
					}
				}
			}
			else if(indexRepresentedByChoice[select]==-1)
			{
				
			}
		}
	}
//	public void displayMenuItemOptions(int select,PlayerActor character)//its not useful anymore, but I still think I may need it someday
//	{
//		Item[] itemSet = Item.getAllItems();
//		int choice=1;
//		int index=0;
//		for(int i=0; i<Item.getNumTypesOfItem();i++)
//		{
//			if(m_inventory[i]>0)
//			{
//				choice++;
//			}
//			if(select==choice)
//			{
//				//selectedItem=itemSet[i];//TODO variable cannot be resolved
//				index=i;
//			}
//		}
//		if(m_inventory[index]==1)
//		{
//			System.out.println("Would you like to buy one " + itemSet[index].getName() + "?\n1) Yes\n2)No");
//			select=myScanner.nextInt();
//			if(select==1)
//			{
//				purchase(index,character);
//			}
//			else
//			{
//				return;
//			}
//		}
//		else if(m_inventory[index]>1)
//		{
//			System.out.println("You can buy up to " + m_inventory[index] + "Of these, how many do you want?");
//			int select2=myScanner.nextInt();
//			if(select2>m_inventory[index])
//			{
//				System.out.println("Sorry, we don't have that many " + itemSet[index].getName() + "s");
//			}
//			else if(select2<0)
//			{
//				System.out.println("Sorry, we cant give you a negative number of something, but you can sell it to us!");
//			}
//			else if(select2==0)
//			{
//				System.out.println("Sorry, would you like to buy something else?");
//			}
//			else
//			{
//				purchaseMultiple(index,select2,character);
//			}
//		}
//	}
	public void purchase(int index,PlayerActor character)
	{
		Item[] itemSet = Item.getAllItems();
		if(character.canBuyItem(itemSet[index].getValue()))//puchase is successful
		{
			//character.getInventory()[index]++;//TODO must be array type
			if(character.buyItem(index))
			{
				System.out.println("Puchase successful! You have bought one " + itemSet[index].getName());
				this.m_inventory[index]--;
			}
			else
			{
				System.out.println("ERROR purchase not successful, please try again");
			}
		}
		else
		{
			System.out.println("Sorry, you don't have enough money!");
		}
	}
	public void purchaseMultiple(int index, int quantity, PlayerActor character)
	{
		Item[] itemSet = Item.getAllItems();
		if(character.canBuyItems(index, quantity))//purchase is successful
		{
			//character.getInventory()[index]+=quantity;//TODO must be array type
			if(character.buyItems(index,quantity))
			{
				System.out.println("Purchase successful! You have bought" + quantity + " " +itemSet[index]);
				this.m_inventory[index]-=quantity;
			}
			this.m_inventory[index]-=quantity;
		}
		else
		{
			System.out.println("Sorry, you don't have enough money!");
			
		}
	}
}
