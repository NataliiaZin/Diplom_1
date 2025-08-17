package praktikum.burger;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BurgerParametrizedTest {

    private final IngredientType ingredientType;
    private final String ingredientName;
    private final float ingredientPrice;

    public BurgerParametrizedTest(IngredientType ingredientType, String ingredientName, float ingredientPrice) {
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.ingredientPrice = ingredientPrice;
    }

    @Parameterized.Parameters(name = "Тип ингридиента: {0}, название ингридиента: {1}, Цена: {2} ")
    public static Collection<Object[]> ingredientData() {
        return Arrays.asList(new Object[][]{
                {IngredientType.FILLING, "Флюоресцентная булка R2-D3", Float.MAX_VALUE},
                {IngredientType.SAUCE, "Краторная булка N-200i", -Float.MAX_VALUE}
        });
    }

    @Test
    public void addIngredientTest() {
        Burger burger = new Burger();
        Ingredient ingredient = new Ingredient(ingredientType, ingredientName, ingredientPrice);
        burger.addIngredient(ingredient);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(1).isEqualTo(burger.ingredients.size());
        softly.assertThat(burger.ingredients.get(0)).isEqualTo(ingredient);
        softly.assertAll();
    }
}
