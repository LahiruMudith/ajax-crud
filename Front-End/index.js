loadData();
var tableData = [];
function loadData(){
    $('#main-card-div').empty();
    $.ajax({
        url: "http://localhost:8080/APP1_Web_exploded/event",
        method: "GET",
        success: function(res) {
            tableData = res;
            tableData.forEach(function (event){
                let card_data = `<div class="card m-1" style="width: 20rem;">
                    <ul class="list-group list-group-flush ">
                        <li class="list-group-item">ID: ${event.eid}</li>
                        <li class="list-group-item">Name: ${event.ename}</li>
                        <li class="list-group-item">Description: ${event.edescription}</li>
                        <li class="list-group-item">Date: ${event.edate}</li>
                        <li class="list-group-item">Place: ${event.eplace}</li>
                        <li class="list-group-item">
                            <button type="button" data-eid="${event.eid}" class="btn btn-primary me-3 ms-5" id="btn-update">Update</button>
                            <button type="button" data-eid="${event.eid}" class="btn btn-danger ms-3" id="btn-delete">Delete</button>
                        </li>
                        <li class="list-group-item">
                            
                        </li>
                    </ul>
                </div>`
                $('#main-card-div').append(card_data);
            })
        },
        error: function(err) {
            alert("Error Fetching Data")
        },
    })
}

$("#btnRegister").on("click", function(event) {
    event.preventDefault();
    if (event.target.textContent == "Register") {
        $.ajax({
            url: "http://localhost:8080/APP1_Web_exploded/event",
            method: "POST",
            data: JSON.stringify({
                "eid" : $("#eventId").val(),
                "ename" : $("#eventName").val(),
                "edescription" : $("#eventDescription").val(),
                "edate" : $("#eventDate").val(),
                "eplace" : $("#eventPlace").val()
            }),
            contentType: "application/json",
            success : function (res){
                alert("Event Registered Successfully");
                loadData();
                $("#eventId").val("");
                $("#eventName").val("");
                $("#eventDescription").val("");
                $("#eventDate").val("");
                $("#eventPlace").val("");
            },
            error : function (err){
                alert("Error Registering Event");
            }
        });
    }else {
        $("#btnRegister").text("Register");
        $.ajax({
            url: "http://localhost:8080/APP1_Web_exploded/event",
            method: "PUT",
            data: JSON.stringify({
                "eid" : $("#eventId").val(),
                "ename" : $("#eventName").val(),
                "edescription" : $("#eventDescription").val(),
                "edate" : $("#eventDate").val(),
                "eplace" : $("#eventPlace").val()
            }),
            contentType: "application/json",
            success : function (res){
                alert("Event Update Successfully");
                loadData();
                $("#eventId").val("");
                $("#eventName").val("");
                $("#eventDescription").val("");
                $("#eventDate").val("");
                $("#eventPlace").val("");
            },
            error : function (err){
                alert("Error Update Event");
            }
        });
    }
});

$("#main-card-div").on("click", "#btn-update", function(event) {
    const eventId = $(this).data("eid");
    tableData.forEach(function (event){
        if (eventId == event.eid){
            $("#eventId").val(event.eid);
            $("#eventName").val(event.ename);
            $("#eventDescription").val(event.edescription);
            $("#eventDate").val(event.edate);
            $("#eventPlace").val(event.eplace);
        }
    });
    $("#btnRegister").text("Update");
});

$("#main-card-div").on("click", "#btn-delete", function(event) {
    const eventId = $(this).data("eid");
    $.ajax({
        url : "http://localhost:8080/APP1_Web_exploded/event",
        method : "DELETE",
        data : JSON.stringify({
            "eid" : eventId
        }),
        success: function(res) {
            alert("Event Deleted Successfully");
            loadData();
        },
        error: function(err) {
            alert("Error Deleting Event");
        }
    });
});