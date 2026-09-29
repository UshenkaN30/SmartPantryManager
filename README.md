# Smart Pantry Manager

## App Description

Smart Pantry Manager is an Android application developed in Java that helps users manage the ingredients they have available at home. Users can add, view, edit and delete pantry ingredients and receive recipe suggestions based on the ingredients and quantities currently available in their pantry.

The application uses strict recipe matching, which means a recipe is only suggested when all the required ingredients are available in sufficient quantities.

## Main Features

- Add new pantry ingredients
- View stored pantry ingredients
- Edit existing ingredients
- Delete ingredients
- Store ingredient name, quantity and unit
- View suggested recipes based on available pantry ingredients
- Check required ingredient quantities before suggesting a recipe
- Handle common unit conversions such as kilograms to grams and litres to millilitres
- Handle common singular and plural ingredient names
- View all available recipes
- View recipe ingredients and cooking instructions
- Persistent application settings
- Toolbar navigation menu
- Input validation and error handling
- Persistent local data storage

## Database Option

The application uses SQLite through Android's SQLiteOpenHelper class.

SQLite was selected because Smart Pantry Manager requires structured local data storage and must continue to store pantry and recipe information without requiring an internet connection.

The database stores pantry ingredients as well as the application's pre-loaded recipes and their required ingredients. The application includes 15 recipes that are seeded into the SQLite database.

SQLite also supports the CRUD operations required by the application, including creating, reading, updating and deleting pantry ingredients.

## Recipe Matching

Smart Pantry Manager uses strict recipe matching. A recipe is suggested only when every required ingredient is available in the pantry in at least the required quantity.

The matching system also handles common unit differences such as:

- kg and g
- litre and ml
- piece and pieces
- slice and slices

It also normalises common singular and plural ingredient names to improve matching accuracy.

## Technologies Used

- Java
- Android Studio
- SQLite
- SQLiteOpenHelper
- RecyclerView
- SharedPreferences
- Android Intents

## Setup and Run Instructions

1. Clone or download the Smart Pantry Manager repository.
2. Open Android Studio.
3. Select **Open** and choose the SmartPantryManager project folder.
4. Allow Android Studio to complete the Gradle sync.
5. Start an Android emulator or connect a compatible Android device.
6. Click the **Run** button in Android Studio.
7. Select the emulator or connected device.
8. Wait for Smart Pantry Manager to install and open.

## Application Navigation

The main screen provides access to:

- Add Ingredient
- Suggested Recipes
- View All Recipes
- Settings

A toolbar navigation menu also provides access to Suggested Recipes, All Recipes and Settings.

## Data Persistence

Pantry ingredients are stored in SQLite so that the user's data remains available after the application is closed and reopened.

Application settings are stored using SharedPreferences.
