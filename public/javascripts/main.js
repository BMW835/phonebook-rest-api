$(document).ready(function() {
    $('#get_name').click(function() {



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
                var table = "<table border='1'>"
                table += '<tr><td>' + 'Name' + '</td><td>' + 'Phone' + '</td><td>' + 'Delete' + '</td></tr>';
                for(i in result) {
                    table += '<tr>';
                    table += '<td>' + result[i].name + '</td>';
                    table += '<td>' + result[i].phone + '</td>';
                    table += '<td><button type="button" class="deletebtn' + i + '" title="Remove row">Delete ' + result[i].name + '</button></td>';
                    $(document).on('click', 'button.deletebtn' + i, function () {
                    var name = result[i].name;
                        $.ajax({
                            url: 'http://localhost:9000/phone/' + result[i].id,
                            method: 'DELETE',
                            success: function() {
                                $("#name").text(name + ' deleted!');
                            },
                        });
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