$(document).ready(function(){
 
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