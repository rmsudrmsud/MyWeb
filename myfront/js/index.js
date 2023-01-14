$(()=>{
    // menu=productlist
    let queryStr = location.search.substring(1)
    let arr = queryStr.split('=')
    if(arr[0] == 'menu'){
         switch(arr[1]){
         case 'productlist':
             $('header>nav>ul>li.productlist').click()
             break;
         }
    }
 })