$(document).ready(function(){
	$("#buynow").click(function(){
		if($("#totalPrice").text() != 0){
			if(confirm('Are you sure to purchase them?')){
				var selected = new Array();
				$("input[type='checkbox']:checked").each(function(){
					selected.push($(this).val());
				});
				var selectedString = selected.toString();
				$("#selectedProduct").val(selectedString);
				$("#formBuy").submit();
			}
		}
	});

	var totalPrice = 0;
	var isOutOfStock = true;
	$("input[type='checkbox']").change(function(){
		if(this.checked){
			var quantityInStorage = parseInt($(this).closest("td").find("input[id='quantityInStorage']").val());
			var orderQuantity = parseInt($(this).parent().parent().find("input[id='quantity']").val());
			if(orderQuantity > quantityInStorage){
				isOutOfStock = true;
				$(this).prop("checked", false);
				alert("Product selected out of stock!");				
			} else
				isOutOfStock = false;
			if(isOutOfStock == false){
				var x = parseInt($(this).parent().parent().find("td[id='priceProduct']").text().replace(".0$",""));
				totalPrice += x;
				$("#totalPrice").text(totalPrice.toLocaleString());
				$("input[name='TOTAL_PRICE']").val(totalPrice);
			}
		} else{
			var x = parseInt($(this).parent().parent().find("td[id='priceProduct']").text().replace(".0$",""));
			totalPrice -= x;
			$("#totalPrice").text(totalPrice.toLocaleString());
			$("input[name='TOTAL_PRICE']").val(totalPrice);
		}
	});
});