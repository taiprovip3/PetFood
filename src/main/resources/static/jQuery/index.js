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
    
    //Click choose pet type
    $("#chooseDogType").click(function(){
        $("#selectPetType").val("DOG");
        $(this).parent("form").submit();
    });
    $("#chooseCatType").click(function(){
        $("#selectPetType").val("CAT");
        $(this).parent("form").submit();
    });
    

});