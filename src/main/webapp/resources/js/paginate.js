    function getQSP()
    {
   	 var vars = [], hash;
   	 var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
   	 for(var i = 0; i < hashes.length; i++)
   	 {
   	  hash = hashes[i].split('=');
   	  vars.push(hash[0]);
   	  vars[hash[0]] = hash[1];
   	  }
   	 return vars;
   }  
     
   function moveNext(pageroot)
   {
    var qs = getQSP(); 
    off = typeof qs['off'] !== 'undefined' ? qs['off'] : '0';

    var offInt = parseInt(off) + 5;
    rurl = pageroot+"?off={0}".replace('{0}', offInt.toString() );   
    window.location.href = rurl;

   }
     
   function movePrev(pageroot)
   {
    var qs = getQSP(); 
    off = typeof qs['off'] !== 'undefined' ? qs['off'] : '0';
     
    if (off == '0'){
      return
    }  
    else{  
     var offInt = parseInt(off) - 5;
     rurl = pageroot+"?off={0}".replace('{0}', offInt.toString() );
     window.location.href = rurl;
    } 
   }
