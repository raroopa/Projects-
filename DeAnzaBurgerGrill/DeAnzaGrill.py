'''
Author: Roopa Rajagopalan
Team Name: Roopa Rajagopalan
Description: This program demonstrates a menu-driven system for the De Anza Grill Burger Club. 
Users select food items they would like, choose quantities, and receive a bill based on their 
status as either student or staff.
'''

def show_menu():
    '''
    This function displays the burger club menu with prices.
    '''
    # print items and prices
    print("\nDe Anza Grill Burger Club Menu")
    print("1. De Anza Burger: $5.25")
    print("2. Bacon Cheese Burger: $5.75")
    print("3. Mushroom Swiss Burger: $5.95")
    print("4. Western Burger: $5.95")
    print("5. Don Cali Burger: $5.95")
    print("6. Exit")

def get_inputs():
    '''
    This function gets the user input for food selection and quantity.
    
    Returns: 
    dict: A dictionary where keys are menu item numbers and values are the quantities.  
    '''
    prices = {1: 5.25, 2: 5.75, 3: 5.95, 4: 5.95, 5: 5.95}
    order = {}
    
    # get user input
    while True:
        try:
            choice = int(input("\nEnter your choice (1-6): ").strip())

            if choice == 6 and not order:
                print("Thank you, hope to see you again!")
                return None
            
            elif choice == 6:
                break
            
            elif choice in prices:
                quantity = int(input("Enter the quantity: ").strip())
                
                if quantity < 0:
                    print("Please enter a positive quantity.")
                    continue
                
                order[choice] = order.get(choice, 0) + quantity
            
            else:
                print("Invalid input. Please select a number between 1 and 6.")
        
        except ValueError:
            print("Invalid input. Please enter a number.")
    
    return order

def compute_bill(order):
    '''
    This function calculates the total bill before and after tax based on the user's order.
    
    Parameters:
    order (dict): A dictionary where keys are menu item numbers and values are the quantities.
    
    Returns:
    tuple: A tuple containing the subtotal, tax, and total bill (including tax).
    '''
    prices = {1: 5.25, 2: 5.75, 3: 5.95, 4: 5.95, 5: 5.95}
    
    # calculate subtotal
    subtotal = sum(prices[item] * quantity for item, quantity in order.items())

    # get user input until input is valid and one of 4 options
    while True:
        user_type = input("Are you a student or staff? ").strip().lower()
       
        if user_type in ["student", "staff"]:
            break
        
        else:
            print("Invalid input. Please enter 'student' or 'staff'. Try again.")

    # calculate tax if user is a staff
    tax = 0.09 * subtotal if user_type == 'staff' else 0.0
    
    total = subtotal + tax
    
    # round answer
    return round(subtotal, 2), round(tax, 2), round(total, 2)

def print_bill(subtotal, tax, total, order):
    '''
    This function displays the final bill with all details.

    Parameters:
    subtotal (float): The total price of the order before tax.
    tax (float): The tax amount the user will pay.
    total (float): The final bill including tax.
    order (dict): A dictionary where keys are menu item numbers and values are the quantities.
    '''
    prices = {1: 5.25, 2: 5.75, 3: 5.95, 4: 5.95, 5: 5.95}
    
    item_names = {1: "De Anza Burger", 2: "Bacon Cheese Burger", 3: "Mushroom Swiss Burger", 4: "Western Burger", 5: "Don Cali Burger"}
    
    print("\n-----Bill Summary------")
    print("-----------------------")
    
    # print items, quantities, and total prices for each item based on quantity
    for item, quantity in order.items():
        print(f"{item_names[item]} (x{quantity}): ${prices[item] * quantity:.2f}")
    
    print("-----------------------")
    print(f"Subtotal: ${subtotal:.2f}")
    print(f"Tax: ${tax:.2f}")
    print(f"Total: ${total:.2f}")

def main():
    '''
    This function is the main function that carries out all separate functions.
    '''
    show_menu()
    order = get_inputs()
    
    if order:
        subtotal, tax, total = compute_bill(order)
        print_bill(subtotal, tax, total, order)

if __name__ == "__main__":
    main()

"""
EXAMPLE OUTPUT 1:

De Anza Grill Burger Club Menu
1. De Anza Burger: $5.25
2. Bacon Cheese Burger: $5.75
3. Mushroom Swiss Burger: $5.95
4. Western Burger: $5.95
5. Don Cali Burger: $5.95
6. Exit

Enter your choice (1-6): 5
Enter the quantity: 2

Enter your choice (1-6): 3
Enter the quantity: 4

Enter your choice (1-6): 1
Enter the quantity: 2

Enter your choice (1-6): 2
Enter the quantity: 6

Enter your choice (1-6): 6
Are you a student or staff? Staff

-----Bill Summary------
-----------------------
Don Cali Burger (x2): $11.90
Mushroom Swiss Burger (x4): $23.80
De Anza Burger (x2): $10.50
Bacon Cheese Burger (x6): $34.50
-----------------------
Subtotal: $80.70
Tax: $7.26
Total: $87.96

EXAMPLE OUTPUT 2:

De Anza Grill Burger Club Menu
1. De Anza Burger: $5.25
2. Bacon Cheese Burger: $5.75
3. Mushroom Swiss Burger: $5.95
4. Western Burger: $5.95
5. Don Cali Burger: $5.95
6. Exit

Enter your choice (1-6): 3
Enter the quantity: -4
Please enter a positive quantity.

Enter your choice (1-6): 4
Enter the quantity: 2

Enter your choice (1-6): -5
Invalid input. Please select a number between 1 and 6.

Enter your choice (1-6): Hi
Invalid input. Please enter a number.

Enter your choice (1-6): 7
Invalid input. Please select a number between 1 and 6.

Enter your choice (1-6): 5
Enter the quantity: 3

Enter your choice (1-6): 6 
Are you a student or staff? Staffs
Invalid input. Please enter 'student' or 'staff'. Try again.
Are you a student or staff? Invalid
Invalid input. Please enter 'student' or 'staff'. Try again.
Are you a student or staff? Student

-----Bill Summary------
-----------------------
Western Burger (x2): $11.90
Don Cali Burger (x3): $17.85
-----------------------
Subtotal: $29.75
Tax: $0.00
Total: $29.75
"""