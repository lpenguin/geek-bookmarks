$(function(){
  var search = $("#search");
  $.get('/api/tags', function(json){
    console.log(json);
    var tags = json.result;

    var $select = search.selectize({
      create: true,
      options: tags.map(function(item){
        return {
          text: item.tag,
          value: item.tag
        }
      })
    })
  })
})
