/* 
 * Responsive functions to manage the list of ingredients Datatable.
 * (used in the recipe editor)
 */

$(document).ready(function() {
    if ($(window).width() < 681) {
        table = mobile();
    } else {
        table = other();
    }
} );            

/**
 * Mobile phone: show all table entries.
 * @returns the datatable
 */
function mobile() {

    var table = $('#list_current_ingredients').DataTable( {
        "info":           false,
        "paging":         false,
        "bFilter":        false
    } );
 
    $('#list_current_ingredients tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('active') ) {
            $(this).removeClass('active');
        }
        else {
            table.$('tr.active').removeClass('active');
            $(this).addClass('active');
        }

        // Set a variable with the ID of the active row, to be used as a GET parameter:
        var id = table.rows('.active').data()[0][0];
        $('#current_ingredient_selected').val(id);
    } );
 
    $('#removeIngredientButton').click( function () {
        table.row('.active').remove().draw( false );
    } );
}            

/**
 * Tablets and PC's: limit table size. Scroll.
 * @returns the datatable
 */
function other() {

    var table = $('#list_current_ingredients').DataTable( {
        "paging":         false,
        "bFilter":        false
    } );
 
    $('#list_current_ingredients tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('active') ) {
            $(this).removeClass('active');
        }
        else {
            table.$('tr.active').removeClass('active');
            $(this).addClass('active');
        }

        // Set a variable with the ID of the active row, to be used as a GET parameter:
        var id = table.rows('.active').data()[0][0];
        $('#current_ingredient_selected').val(id);
    } );
 
    $('#removeIngredientButton').click( function () {
        table.row('.active').remove().draw( false );
        calculateCalories();
    } );
}            
