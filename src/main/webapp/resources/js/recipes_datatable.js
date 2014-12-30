/* 
 * Responsive functions to manage the list of recipes Datatable.
 * (used in the maintenance menu)
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

    var table = $('#recipelist').DataTable( {
        "info":           false,
        "paging":         false
    } );
 
    $('#recipelist tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('active') ) {
            $(this).removeClass('active');
        }
        else {
            table.$('tr.active').removeClass('active');
            $(this).addClass('active');
        }

        // Set a variable with the ID of the active row, to be used as a GET parameter:
        var id = table.rows('.active').data()[0][0];
        $('#selected_recipe').val(id);
    } );
 
    $('#button').click( function () {
        table.row('.active').remove().draw( false );
    } );
}            

/**
 * Tablets and PC's: limit table size. Scroll.
 * @returns the datatable
 */
function other() {

    var table = $('#recipelist').DataTable( {
        "scrollY":        "200px",
        "scrollCollapse": true,
        "paging":         false
    } );
 
    $('#recipelist tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('active') ) {
            $(this).removeClass('active');
        }
        else {
            table.$('tr.active').removeClass('active');
            $(this).addClass('active');
        }

        // Set a variable with the ID of the active row, to be used as a GET parameter:
        var id = table.rows('.active').data()[0][0];
        $('#selected_recipe').val(id);
    } );
 
    $('#button').click( function () {
        table.row('.active').remove().draw( false );
    } );
}            

/**
 * Set focus in the ingredient finder.
 * Show detail of first element of the list.
 */
function selectFirstRecipe() {
    $('#recipelist').DataTable().$('tr:first').click();
}

