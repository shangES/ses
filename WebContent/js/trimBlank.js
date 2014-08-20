//20040209£¬É¾³ýÊ×Î²¿Õ¸ñ£»
function  trimBlank(d)
{
        var z="";
        var x="";
         for(var i=0;i<d.length;i++)
         {
                x=d.substring(i,i+1);              
          	if(x!=" ")
          	{
                     z=d.substring(i);
                     break;
                }
         }
         d=z;
         for(var j=d.length;j>0;j--)
         {
             x=d.substring(j-1,j);
             if(x!=" ")
             {
                    z=d.substring(0,j);                    
                    break;
             }             
         }
         return(z);
}