/* 
 * Recipes: javascript functions.
 */

// RECIPE EDITION:

/**
 * Add ingredient to recipe's ingredients list
 */
function addIngredient() {
    // Retrieve new ingredient data:
    var id = $('#new_ingredient_selected_id').val();
    var name = $('#new_ingredient_selected_name').val();
    
    var table = $('#list_current_ingredients').DataTable();

    table.row.add( [ 
            id,
            name
         ] )
        .draw();
    
    calculateCalories();
}

/**
 * Calculate current calories
 */
function calculateCalories() {
    $('#total_calories').html("");
    var total = 0;
    
    $("#ingredients option").each(function(index, option)
    {
        $.getJSON("get_ingredient",
            {
                id_ingredient: option.value
            },
            function(ingredient) {
                total += ingredient.calories;
                $('#total_calories').html(total);
            }
        );
    });
}

/**
 * Save the ingredients from the table into an INPUT parameter.
 */
function selectAllIngredients() {
    var ids = new Array();
    
    var oTable = $('#list_current_ingredients').dataTable();
    var data = oTable.fnGetData();
    for (var i=0; i<data.length; i++) {
        ids.push(data[i][0]);
    }
    
    $('#ingredients_ids').val(ids);
}

// RECIPE FINDER:

/**
 * View data from selected recipe. 
 */
function viewRecipeDetail(idRecipe) {

    // Select recipe (set id hidden form field) and let it ready for the GET:
    $('#selected_recipe').val(idRecipe);

    // Go to recipe button is activated:
    $('#goto_button').css('display','inline');

    // Retrive details and populate the corresponding fields:
    $.getJSON("get_recipe",
            {
                id: idRecipe
            },
            function(recipe) {
                $('#recipe_name').html(recipe.nombre);
                $('#recipe_description').html("<em>Description:</em> " + recipe.description);
                $('#recipe_calories').html("<em>Calories:</em> " + recipe.calories);

                var listIngredients = "<em>Ingredients:</em> ";
                for (var i = 0; i < recipe.ingredients.length; i++) {
                    if (i > 0) {
                        listIngredients += ", ";
                    }
                    
                    listIngredients += recipe.ingredients[i].name;
                }
                
                $('#recipe_ingredients').html(listIngredients + ".");
            }
    );
}

/**
 * Set focus in the recipe finder.
 */
function setFocusRecipeFinder() {
    var list = document.getElementById('list_recipes');
    
    if (list.options.length > 0) {
        list.selectedIndex = 0; 
        list.focus();
        viewRecipeDetail();
    }
}

// INGREDIENTS FINDER:

/**
 * Retrieve data from selected ingredient. 
 */
function viewIngredientDetail(idIngredient) {
    //var listIngredients = document.getElementById('list_ingredients');
    //var idIngredient = listIngredients.options[listIngredients.selectedIndex].value;

    $.getJSON("get_ingredient",
            {
                id_ingredient: idIngredient
            },
            function(ingredient) {
                $('#ingredient_detail').html(ingredient.info);
            }
    );
}

/**
 * Set focus in the ingredient finder.
 * Show detail of first element of the list.
 */
function setFocusIngredientFinder() {
    var list = document.getElementById('list_ingredients');
    var text = document.getElementById('name');
    
    if (list.options.length > 0) {
        list.selectedIndex = 0; 
        list.focus();
        viewIngredientDetail();
    } else {
        text.focus();
    }
}

// RECIPES EDITION: NEW INGREDIENTS FINDER:

/**
 * Ingredients finder
 */
function searchIngredients() {
    var name = document.getElementById('search_name').value;
    if (!name) return;

    $.getJSON("get_ingredients_list",
            {
                name: name
            },
            function(ingredients) {
                $('#list_new_ingredients').empty();
                
                for (var i = 0; i < ingredients.length; i++) {
                    
                    var name = ingredients[i].food_name;
                    var funct = "viewNewIngredientDetail( " + ingredients[i].food_id + " , '" + name + "')";
                    
                    $('#list_new_ingredients').append(
                                        '<a href="#list_new_ingredients" onclick="' + funct + '" class="list-group-item">' +
                                            '<span class="text-primary"><b>' + ingredients[i].food_name + '</b></span>' +
                                        '</a>');
                }

                // Select first item:
                selectFirstNewIngredient(0);
            }
    );
}

/**
 * Select the first item in the new ingredients list.
 */
function selectFirstNewIngredient() {
    $('#list_new_ingredients option')[0].selected = true;
    $('#list_new_ingredients').focus();

    viewNewIngredientDetail();
}

/**
 * Store the value of the selected ingredient (in the current ingredients list)
 * The id is used to remove items from this list.
 * @param {type} idIngredient
 */
function selectCurrentIngredient(idIngredient) {
    $('#current_ingredient_selected').val(idIngredient);
}

/**
 * Retrieve data from selected ingredient. 
 * @param {type} idIngredient
 * @param {type} ingredientName
 */
function viewNewIngredientDetail(idIngredient, ingredientName) {
    $('#new_ingredient_selected_id').val(idIngredient);
    $('#new_ingredient_selected_name').val(ingredientName);

    $.getJSON("get_ingredient",
            {
                id_ingredient: idIngredient
            },
            function(ingredient) {
                $('#new_ingredient_detail').html(ingredient.info);
            }
    );
}
