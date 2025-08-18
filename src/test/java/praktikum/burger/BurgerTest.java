package praktikum.burger;


import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static praktikum.util.NumUtils.getRandFloat;

public class BurgerTest {

    private static final String FILLING_INGREDIENT_NAME = "Биокотлета из марсианской Магнолии";
    private static final String SAUCE_INGREDIENT_NAME = "Соус Spicy-X";
    private static final String BUN_NAME = "Краторная булка N-200i";
    private static final float COMPARISON_FLOAT_DELTA = 0.0f;
    private Burger burger;
    private Ingredient fillingIngredient;
    private Ingredient sauceIngredient;
    private Bun bun;

    @Before
    public void setUp() {
        burger = new Burger();
        fillingIngredient = mock(Ingredient.class);
        when(fillingIngredient.getType()).thenReturn(IngredientType.FILLING);
        when(fillingIngredient.getName()).thenReturn(FILLING_INGREDIENT_NAME);
        when(fillingIngredient.getPrice()).thenReturn(getRandFloat());

        sauceIngredient = mock(Ingredient.class);
        when(sauceIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(sauceIngredient.getName()).thenReturn(SAUCE_INGREDIENT_NAME);
        when(sauceIngredient.getPrice()).thenReturn(getRandFloat());

        bun = mock(Bun.class);
        when(bun.getName()).thenReturn(BUN_NAME);
        when(bun.getPrice()).thenReturn(getRandFloat());
    }

    @Test
    public void setBunsTest() {
        burger.setBuns(bun);
        assertEquals(burger.bun, bun);
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);
        burger.removeIngredient(1);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(1).isEqualTo(burger.ingredients.size());
        softly.assertThat(burger.ingredients.get(0)).isEqualTo(sauceIngredient);
        softly.assertAll();
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);
        burger.moveIngredient(0, 1);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(burger.ingredients.get(0)).isEqualTo(fillingIngredient);
        softly.assertThat(burger.ingredients.get(1)).isEqualTo(sauceIngredient);
        softly.assertAll();
    }

    @Test
    public void getPriceTest() {
        burger.setBuns(bun);
        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);
        float expectedPrice = (sauceIngredient.getPrice() + fillingIngredient.getPrice()) + bun.getPrice() * 2;
        assertEquals(expectedPrice, burger.getPrice(), COMPARISON_FLOAT_DELTA);
    }

    @Test
    public void getReceiptTest() {
        burger.setBuns(bun);
        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);
        String expectedReceipt = getExpectedReceipt(bun, sauceIngredient, fillingIngredient);
        assertEquals(expectedReceipt, burger.getReceipt());
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