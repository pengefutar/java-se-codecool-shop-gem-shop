

// Ajax proba
$(document).ready(function(){

    var postAjax = function(productId){
        $.ajax({
            url: "/add-product/" + productId,
            type: "POST",
            dataType: "json",
            data: {
                success: "ok"
            },
            success: function(data){
                console.log("OK");
            },
            error: function(data){
                console.log(data);
                console.log("not okay");
            }
        });
    };

    $(".add-product").click(function(){
        var productId = $(this).attr("id");
        postAjax(productId);
    })

});
