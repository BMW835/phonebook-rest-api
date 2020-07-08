$(document).ready(function() {

    $('#add_phone').click(function() {
        var phoneForm = {
            phoneNumber:$("#input_phone").val(),
            name: $("#input_name").val()
        }
        $.ajax({
            url: '/phones/createNewPhone',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(phoneForm),
            complete: function() {
                $("#status").text(phoneForm.name + ' saved!');
                $.all();
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    });

    $('#all').click(function() {
        $.all();
    });
    $.all = function() {
        jsRoutes.controllers.HomeController.getPhones().ajax({
            success: function(result) {
                createTable(result);
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    }

    $('#searchByName').click(function() {
        var inputName = $("#input_name").val();
        jsRoutes.controllers.HomeController.byName(inputName).ajax({
            success: function(result) {
                createTable(result);
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    });

    $('#searchByPhone').click(function() {
        var inputPhone = $("#input_phone").val();
        jsRoutes.controllers.HomeController.byPhone(inputPhone).ajax({
            success: function(result) {
                createTable(result);
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    });

    $('#searchById').click(function() {
        var inputId = $("#input_id").val();
        jsRoutes.controllers.HomeController.byId(inputId).ajax({
            success: function(result) {
                createTable(result);
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    });

    $('#print_phone').click(function() {
        var phoneForm = {
            phoneNumber:$("#input_phone").val(),
            name: $("#input_name").val()
        }
        $.ajax({
            url: '/phones/printPhone',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(phoneForm),
            complete: function() {
                $("#status").text(phoneForm.name + ' saved and printed to /tmp/' + phoneForm.name + '.txt');
                $.all();
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    });

    $('#read_phone').click(function() {
        var inputPath = $("#input_path").val();
        jsRoutes.controllers.HomeController.readPhone(inputPath).ajax({
            success: function(result) {
                $("#status").text('Contact from ' + inputPath + ' saved!');
                $.all();
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    });

    function createTable(result) {
        $('#main_table').empty();
        var table = "<table border='1'>"
        table += '<tr><td><b>Name</b></td><td><b>Phone</b></td><td><b>Delete</b></td><td><b>Update</b></td></tr>';
        for(i in result) {
            table += '<tr>';
            table += '<td>' + result[i].name + '</td>';
            table += '<td>' + result[i].phone + '</td>';
            table += '<td><button type="button" id="' + result[i].id + '"class="deletebtn" title="Delete Contact">Delete</button></td>';
            table += '<td><button type="button" id="' + result[i].id + '"class="updatebtn" title="Update Contact">Update ' + '</button></td>';
            table += '</tr>';
        }
        table += "</table>"
        $('#main_table').append(table);
    };

    $(document).on('click', 'button.deletebtn', function () {
        var id = $(this).attr('id');
        $.ajax({
            url: '/phone/' + id,
            type: 'DELETE',
            success: function() {
                $("#status").text('id ' + id + ' deleted');
                $.all();
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    });

    $(document).on('click', 'button.updatebtn', function () {
        var id = $(this).attr('id');
        var phoneForm = {
            phoneNumber:$("#input_phone").val(),
            name: $("#input_name").val()
        }
        $.ajax({
            url: '/phone/' + id,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(phoneForm),
            complete: function() {
                $("#status").text(phoneForm.name + ' updated');
                $.all();
            },
            failure: function(err) {
                $("#status").text('There was an error');
            }
        });
    });

    $.all();

});