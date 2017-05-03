
$(document).ready(function(){

    var increaseQuantity = function(productId){
        $.ajax({
            url: "/increase-product/" + productId,
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

    var addToCart = function(productId){
        $.ajax({
            url: "/add-product/" + productId,
            type: "POST",
            dataType: "json",
            data: {
                success: "ok"
            },
            success: function(data){
                console.log(data);
                var obj = JSON.parse(JSON.stringify(data));
                document.getElementById("render-to-shopping-cart").innerHTML = "Shopping cart (" + obj.numOfLineItems + ")";
            },
            error: function(data){
                console.log(data);
                console.log("not okay");
            }
        });
    };

    $(".increase-quantity").click(function(){
        var productId = $(this).attr("id");
        increaseQuantity(productId);
    })

    $(".add-to-cart").click(function(){
        var productId = $(this).attr("id");
        addToCart(productId);
    })



});
