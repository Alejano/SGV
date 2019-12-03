 function Scrol(documento, tabla){
    	 $(document).scrollTop(documento);
    	 $(".fixed_header tbody").scrollTop(tabla);
    }
    function scroller(id,Dato){
		 var opcion = confirm("Seguro que desea cambiar a "+Dato+"?");
		    if (opcion == true) {
		    	var corDocument = $(document).scrollTop();	
				var corElement = $(".fixed_header tbody").scrollTop();
				var direccion = $("#"+id).attr("href");
				var pagina= getUrlParameter('page')	
				if(pagina==null){pagina=0;}
				var Redirigir=direccion+"/"+ corDocument +"/"+corElement+"?page="+pagina;
				$("#"+id).attr('href',Redirigir);
		    	//alert("link "+$("#"+id).attr('href'));		    	
		    	location.href = $("#"+id).attr('href');
			} else {			   
			 	   
			    return false;
			}
    }
    
    var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};
	
	$(".signos").keypress(function (key) {
        if ((key.charCode < 48 || key.charCode > 57)//numeros        
            )
            return false;
    });
	$(".soloLetras").keypress(function (key) {
        if ((key.charCode < 97 || key.charCode > 122)//letras mayusculas
                && (key.charCode < 65 || key.charCode > 90)//letras mayusculas
                && (key.charCode < 48 || key.charCode > 57)//numeros 
            )
            return false;
    });
	
	$(".signo").keypress(function (key){
		if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
	            (e.keyCode == 65 && e.ctrlKey === true) || 
	            (e.keyCode >= 35 && e.keyCode <= 39)) {
	                 return;
	        }
	 
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	});