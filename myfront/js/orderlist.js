$(()=>{
    let url = backURL + "order/list"

    let $origin = $('div.orderlist>table>tbody>tr').first() //원본
    let $parent = $origin.parent() //$('div.orderlist>table>tbody')
    $.ajax({
        xhrFields: {
            withCredentials: true
         },
        url : url,
        success : function(jsonObj){
            if(jsonObj.status <1){
                alert(jsonObj.msg)
                return
            }
            $(jsonObj).each((index, orderInfo)=>{
                console.log(orderInfo)
                let orderNo = orderInfo.orderNo
                let orderDt = orderInfo.orderDt
                let lines = orderInfo.lines

                let lineSize = lines.length // 주문상세들
                let $copy = $origin.clone(); //복제본
                $copy.find('td.orderNo').html(orderNo)
                $copy.find('td.orderDt').html(orderDt)
                let linesStr = ''
                $(orderInfo.lines).each((index, orderLine)=>{
                    linesStr += '<div>'
                    linesStr += '<ul>'
                    linesStr += '<li>'
                    linesStr += orderLine.orderP.prodNo
                    linesStr += '</ll>'
                    linesStr += '<li>'
                    linesStr += orderLine.orderP.prodName
                    linesStr += '</ll>'
                    linesStr += '<li>'
                    linesStr += orderLine.orderP.prodPrice
                    linesStr += '</ll>'
                    // linesStr += '<li>'
                    // linesStr += orderLine.orderquantity
                    // linesStr += '</ll>'
                    linesStr += '</ul>'
                    linesStr += '</div>'
                })
                $copy.find('td.lines').html(linesStr)
                $parent.append($copy)
            })
            $origin.hide()
        }, error:function(xhr){
            //상태코드값을 구분해보고싶다면?
            alert(xhr.status + ":" + xhr.responseText)
        }
    })
})