package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    public void indexPageShouldReturnIndexTemplate() {

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(1l);

        recipes.add(recipe);
        String indexTemplate = "index";

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String templateReturned = indexController.getIndexPage(model);

        //then
        assertEquals(indexTemplate, templateReturned);
        Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(model, Mockito.times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

}