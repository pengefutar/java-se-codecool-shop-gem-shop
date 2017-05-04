$(document).ready(function () {

    var increaseQuantity = function (lineItemId) {
        $.ajax({
            url: "/increase-lineitem/" + lineItemId,
            type: "POST",
            dataType: "json",
            data: {
                success: "ok"
            },
            success: function (data) {
                var obj = JSON.parse(JSON.stringify(data));
                document.getElementById("lineitem-quantity").innerHTML = "Quantity: "
                    + obj.quantity;
                document.getElementById("lineitem-price").innerHTML = obj.price + " "
                    + obj.currency;
                document.getElementById("totalprice").innerHTML = "Total Price: "
                    + obj.totalprice + " " + obj.currency;
            },
            error: function (data) {
                console.log("not okay");
            }
        });
    };


    var decreaseQuantity = function (lineItemId) {
        $.ajax({
            url: "/decrease-lineitem/" + lineItemId,
            type: "POST",
            dataType: "json",
            data: {
                success: "ok"
            },
            success: function (data) {
                var obj = JSON.parse(JSON.stringify(data));
                if (obj.status === "exists") {
                    document.getElementById("lineitem-quantity").innerHTML = "Quantity: "
                        + obj.quantity;
                    document.getElementById("lineitem-price").innerHTML = obj.price + " "
                        + obj.currency;
                    document.getElementById("totalprice").innerHTML = "Total Price: "
                        + obj.totalprice + " USD";
                }
                else if (obj.status === "deleted") {
                    document.getElementById("lineitem" + obj.id).remove();
                    document.getElementById("totalprice").innerHTML = "Total Price: "
                        + obj.totalprice + " USD";
                }
                else {
                    console.log("non-existent status in json");
                }
            },
            error: function (data) {
                console.log("not okay");
            }
        });
    };

    var addToCart = function (productId) {
        $.ajax({
            url: "/add-product/" + productId,
            type: "POST",
            dataType: "json",
            data: {
                success: "ok"
            },
            success: function (data) {
                var obj = JSON.parse(JSON.stringify(data));
                document.getElementById("render-to-shopping-cart").innerHTML = " "
                    + obj.numOfLineItems;
            },
            error: function (data) {
                console.log("not okay");
            }
        });
    };

    $(".increase-quantity").click(function () {
        var lineItemId = $(this).attr("id");
        increaseQuantity(lineItemId);
    })

    $(".decrease-quantity").click(function () {
        var lineItemId = $(this).attr("id");
        decreaseQuantity(lineItemId);
    })

    $(".add-to-cart").click(function () {
        var productId = $(this).attr("id");
        addToCart(productId);
    })


});
