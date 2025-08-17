package praktikum.burger;


import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.assertEquals;
import static praktikum.util.NumUtils.getRandFloat;

public class BurgerTest {

    private static final String FILLING_INGREDIENT_NAME = "Биокотлета из марсианской Магнолии";
    private static final String SAUCE_INGREDIENT_NAME = "Соус Spicy-X";
    private static final String BUN_NAME = "Краторная булка N-200i";
    private static final float COMPARISON_FLOAT_DELTA = 0.0f;
    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsTest() {
        Bun bun = createBun();
        burger.setBuns(bun);
        assertEquals(burger.bun, bun);
    }

    @Test
    public void removeIngredientTest() {
        Ingredient firstIngredient = createIngredient(IngredientType.FILLING);
        Ingredient secondIngredient = createIngredient(IngredientType.SAUCE);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.removeIngredient(1);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(1).isEqualTo(burger.ingredients.size());
        softly.assertThat(burger.ingredients.get(0)).isEqualTo(firstIngredient);
        softly.assertAll();
    }

    @Test
    public void moveIngredientTest() {
        Ingredient firstIngredient = createIngredient(IngredientType.FILLING);
        Ingredient secondIngredient = createIngredient(IngredientType.SAUCE);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.moveIngredient(0, 1);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(burger.ingredients.get(0)).isEqualTo(secondIngredient);
        softly.assertThat(burger.ingredients.get(1)).isEqualTo(firstIngredient);
        softly.assertAll();
    }

    @Test
    public void getPriceTest() {
        Ingredient firstIngredient = createIngredient(IngredientType.FILLING);
        Ingredient secondIngredient = createIngredient(IngredientType.SAUCE);
        Bun bun = createBun();
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        float expectedPrice = (firstIngredient.getPrice() + secondIngredient.getPrice()) + bun.getPrice() * 2;
        assertEquals(expectedPrice, burger.getPrice(), COMPARISON_FLOAT_DELTA);
    }

    @Test
    public void getReceiptTest() {
        Ingredient firstIngredient = createIngredient(IngredientType.FILLING);
        Ingredient secondIngredient = createIngredient(IngredientType.SAUCE);
        Bun bun = createBun();
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        String expectedReceipt = getExpectedReceipt(bun, firstIngredient, secondIngredient);
        assertEquals(expectedReceipt, burger.getReceipt());
    }

    private Bun createBun() {
        return new Bun(BUN_NAME, getRandFloat());
    }

    private Ingredient createIngredient(IngredientType ingredientType) {
        switch (ingredientType) {
            case FILLING:
                return new Ingredient(ingredientType, FILLING_INGREDIENT_NAME, getRandFloat());
            case SAUCE:
                return new Ingredient(ingredientType, SAUCE_INGREDIENT_NAME, getRandFloat());
            default:
                throw new RuntimeException("Unsupported ingredient type: " + ingredientType);
        }
    }

    private String getExpectedReceipt(Bun bun, Ingredient firstIngredient, Ingredient secondIngredient) {
        return String.format(
                "(==== %s ====)%n" +
                        "= %s %s =%n" +
                        "= %s %s =%n" +
                        "(==== %s ====)%n%n" +
                        "Price: %f%n",
                bun.getName(),
                firstIngredient.getType().toString().toLowerCase(),
                firstIngredient.getName(),
                secondIngredient.getType().toString().toLowerCase(),
                secondIngredient.getName(),
                bun.getName(),
                burger.getPrice()
        );
    }
}