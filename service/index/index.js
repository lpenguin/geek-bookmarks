var inc_field = function(obj, name){
  if(obj[name] == undefined){
    obj[name] = 0;
  }
  obj[name] += 1;
};

var array_in = function(array, items){
  console.log(items);
  return items.reduce(function(acc, item){
    return acc && (array.indexOf(item) > -1);
  }, true);
};

var find_tags = function(selected_tags){
  var res = records.filter(function(tags){
    return array_in(tags, selected_tags);
  }).reduce(function(acc, tags){
    for(var i in tags){
      inc_field(acc, tags[i]);
    }
    return acc;
  }, {});
  return Object.keys(res).map(function(tag){
    return {
      "tag": tag,
      "count": res[tag]
    }
  }).sort(function(a, b){
    return b.count - a.count;
  });
};


$(function(){
  records = records.map(function(x){
    return x.tags;
  });

  var tags = $("#tags");
  var found_tags = $("#found_tags");
  $("#submit").click(function(){
    var selected_tags = tags.val().split(" ").map(function(item){
      return item.trim();
    });

    var res = find_tags(selected_tags);
    console.log(res);
    var simply = res.map(function(x){
      return x.tag;
    });
    found_tags.text(JSON.stringify(simply));
  });
});
