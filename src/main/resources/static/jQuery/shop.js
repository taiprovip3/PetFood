$(document).ready(function(){
	
    //Move illus
    $("#scr-intro").click(function(){
        $(document).scrollTop( $("#header").offset().top );
    });
    $("#scr-getstarted").click(function(){
        $(document).scrollTop( $("#getStarted").offset().top );
    });
    $("#scr-contact").click(function(){
        $(document).scrollTop( $("#contact").offset().top );
    });
    
    //Click filter
    $("#selectPetType").on("change", function(){
        var foodType = $(this).val();
        if(foodType === "ALL"){
            $("#dogType").show();
            $("#catType").show();
        }
        if(foodType === "DOG"){
            $("#dogType").show();
            $("#catType").hide();
        }
        if(foodType === "CAT"){
            $("#dogType").hide();
            $("#catType").show();
        }
    });
    
    $("#selectCategory").on("change", function(){
        var category = $(this).val();
        if(category === "ALL")
            $("#categoryType").children("img").attr("src", "../img/category_all.png");
        if(category === "KIBBLE")
            $("#categoryType").children("img").attr("src", "../img/kibble.png");   
        if(category === "DEHYDRATED")
            $("#categoryType").children("img").attr("src", "../img/dehydrated.png");   
        if(category === "CANNED")
            $("#categoryType").children("img").attr("src", "../img/canned.png");
        if(category === "SEMI-MOIST")
            $("#categoryType").children("img").attr("src", "../img/semi_moist.png");   
        if(category === "HOMEMADE")
            $("#categoryType").children("img").attr("src", "../img/homemade.png");   
        if(category === "RAW MEAT-BASED")
            $("#categoryType").children("img").attr("src", "../img/raw_meat-based.png");     
    });

});