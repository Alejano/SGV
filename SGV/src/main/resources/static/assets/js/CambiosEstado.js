 function Scrol(documento, tabla){
    	 $(document).scrollTop(documento);
    	 $(".fixed_header tbody").scrollTop(tabla);
    }
    function scroller(id){
    	//alert("id: "+ id);
    	var corDocument = $(document).scrollTop();	
		var corElement = $(".fixed_header tbody").scrollTop();
		var direccion = $("#"+id).attr("href");
		var pagina= getUrlParameter('page')	
		if(pagina==null){pagina=0;}
		var Redirigir=direccion+"/"+ corDocument +"/"+corElement+"?page="+pagina;
		$("#"+id).attr('href',Redirigir);

		 var opcion = confirm("Seguro que desea cambiar a Automovil?");
		    if (opcion == true) {
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