$(document).ready(function() {
    $('#get_name').click(function() {
        jsRoutes.controllers.HomeController.getPhones().ajax({
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
            var inputName = $("#input_name").val()
            jsRoutes.controllers.HomeController.byName(inputName).ajax({
                success: function(result) {
                    $("#name").text(result[0].name);


                          txt = "";
                          txt += "<table border='1'>"
                          for (x in myObj) {
                            txt += "<tr><td>" + myObj[x].name + "</td></tr>";
                          }
                          txt += "</table>"
                          document.getElementById("demo").innerHTML = txt;
}


                },
                failure: function(err) {
                    var errorText = 'There was an error';
                    $("#name").text(errorText);
                }
            });
        });

});