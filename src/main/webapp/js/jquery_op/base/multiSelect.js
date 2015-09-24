$(document).ready(function()
{
    //Click event of select div
    $('.multiSelectDiv').click(function(e){
    	
    	var text = $(this).find('input[type=text]');
    	var hidden = $(this).find('input[type=hidden]');
    	var target = $(e.target); 	
    	
    	if(target.attr('type') == text.attr('type'))
    	{
    	    $('.multiSelectDiv').find('div').each(function(){
	    	    $(this).animate({opacity: 'hide'}, 300);
	    	});
    	
	    	var div = $(this).find('div');
	    	var offSet = text.offset();
	    	var left = offSet.left;
	    	var top = offSet.top;
	    	
	    	if(div.is(":visible"))
	    	{
	    	    div.animate({opacity: 'hide'}, 300);
	    	}
	    	else
	    	{
	    	   div.css({"top":top+20,"left":left}).animate({opacity:'show'}, 300);
	    	}
    	}
    	//If target is checkbox
    	else if(target.attr('type') == 'checkbox')
    	{
    	    var textVal = '';
    	    var hiddenVal = '';
    	    var selectDivObj = $(this);
    	    var curCheckboxCount = 0;
            var selectedCheckboxCount = 0;
    	    //Get value by selected checkbox
	    	$(this).find('input[type=checkbox][class!=selectAll]').each(function(){
	    	    
	    		if($(this).is(':checked'))
	    		{
	    		    textVal += $(this).parent().parent().find('span').text() + ',';
	    		    hiddenVal += $(this).val() + ',';
	    		    selectedCheckboxCount++;
	    		}
	    		//When exist checkbox not select,set 'select all' to not select 
	    		else
	    		{
	    		    selectDivObj.find('input[type=checkbox][class=selectAll]').attr('checked','');
	    		}
	    		curCheckboxCount++;
	    	});
	    	
	    	if(curCheckboxCount>0 && selectedCheckboxCount>0 && curCheckboxCount==selectedCheckboxCount)
	        {
	           selectDivObj.find('input[type=checkbox][class=selectAll]').attr('checked','true');
	        }
	    	
	    	text.val(textVal.substring(0,textVal.length-1));
	    	hidden.val(hiddenVal.substring(0,hiddenVal.length-1));
    	}
    });
    
    //Hide div when user click other area
    $('html').click(function(e){
    	if($(e.target).isChildAndSelfOf (".multiSelectDiv"))
    	{
    	    return;
    	}
    	else
    	{
	    	$('.multiSelectDiv').find('div').each(function(){
	    	    $(this).animate({opacity: 'hide'}, 300);
	    	});
    	}
    }); 
    
    //Check current elements 
    jQuery.fn.isChildAndSelfOf = function(b){ 
        return (this.closest(b).length > 0); 
    };
    
    //On init
    initMultiSelect();
    
});

//Init select
function initMultiSelect()
{
   $('.multiSelectDiv').each(function(){
       var hiddenVal = $(this).find('input[type=hidden]').val();
       var textVal = '';
       //Select checkBox and set up input value after do query
       if(hiddenVal != '')
       {
	       $(this).find('input[type=checkbox]').each(function(){
	    		if(hiddenVal.indexOf($(this).val()) != -1)
	    		{
	    		    $(this).attr('checked',true);
	    		    textVal += $(this).parent().parent().find('span').text() + ',';
	    		}
	       });
	       $(this).find('input[type=text]').val(textVal.substring(0,textVal.length-1));
       }
   });
}