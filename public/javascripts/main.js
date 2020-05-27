$(document).ready(function() {
    $('#get_name').click(function() {
        jsRoutes.controllers.HomeController.getName().ajax({
            success: function(result) {
                $("#name").text(result);
            },
            failure: function(err) {
                var errorText = 'There was an error';
                $("#name").text(errorText);
            }
        });
    });

    $('#set_name').click(function() {
        var inputName = $("#input_name").val()
        jsRoutes.controllers.HomeController.updateName(inputName).ajax({
            success: function(result) {
                $("#name").text(result);
            },
            failure: function(err) {
                var errorText = 'There was an error';
                $("#name").text(errorText);
            }
        });
    });

    $('#searchByName').click(function() {
        $('#main_table').empty();
        var inputName = $("#input_name").val()
        jsRoutes.controllers.HomeController.byName(inputName).ajax({
            success: function(result) {
                $("#name").text(result[0].name);




                var table = "<table border='1'>"
                table += '<tr><td>' + 'Name' + '</td><td>' + 'Phone' + '</td><td>' + 'Delete' + '</td></tr>';



                var test = $('<button>Test</button>').click(function () {
                        alert('hi');
                    });
                table +=  test ;


                for(i in result) {
                    table += '<tr>';
                    table += '<td>' + result[i].name + '</td>';
                    table += '<td>' + result[i].phone + '</td>';
                    table += '<td><button type="button" class="deletebtn' + i + 'n" title="Remove row">Delete ' + result[i].name + result[i].id +'</button></td>';
                    $(document).on('click', 'button.deletebtn' + i +'n', function () {
                                                             alert('hi, ' + result[i].id)
                                                         });


                    table += '</tr>';

                }
                table += "</table>"
                $('#main_table').append(table);
            },
            failure: function(err) {
                var errorText = 'There was an error';
                $("#name").text(errorText);
            }
        });
    });

});