var incField = function(obj, name){
  if(obj[name] == undefined){
    obj[name] = 1;
  }
  obj[name] += 1;
};

// var array_in = function(array, items){
//   console.log(items);
//   return items.reduce(function(acc, item){
//     return acc && (array.indexOf(item) > -1);
//   }, true);
// };

var inArray = function(value, array){
    for(var i = 0; i < array.length; i++){
      if(array[i] == value){
        return true;
      }
    }
    return false;
}

var arrayInArray = function(array1, array2){
  for(var i = 0; i < array1.length; i++){
    if(!inArray(array1[i], array2)){
      return false;
    }
  }
  return true;
}

var countTags = function(recordsTags, selectedTags, emptyCounts){
  var counts = recordsTags.reduce(function(acc, recordTags){
    if(arrayInArray(selectedTags, recordTags)){
      recordTags.forEach(function(tag){
        acc[tag] += 1;
      });
    }
    return acc;
  }, emptyCounts);

  return counts;
};

var reduceTags = function(records){
  var tagsObj = records.reduce(function(acc, recordTags){
    recordTags.forEach(function(tag){
      acc[tag] = 1;
    });
    return acc;
  }, {});

  return Object.keys(tagsObj).map(function(tag){
    return {
      "text": tag,
      "value": tag
    }
  });
}

var clone = function(obj) {
  if(obj == null || typeof(obj) != 'object')
    return obj;

  var temp = obj.constructor(); // changed

  for(var key in obj) {
    if(obj.hasOwnProperty(key)) {
      temp[key] = clone(obj[key]);
    }
  }
  return temp;
}

var getScoreFunction = function(records, emptyCounts){
  var score = function(query){
    var tags = this.getValue().split(",");
    var counts = countTags(records, tags, clone(emptyCounts));
    var scoreF = this.getScoreFunction(query);
    return function(item){
      // console.log([item.text, counts[item.text], scoreF(item), counts[item.text] * scoreF(item)])
      return counts[item.text] * scoreF(item);
    }
  }
  return score;
}

$(function(){
  records = records.map(function(x){
    return x.tags;
  });

  var options = reduceTags(records);

  var emptyCounts = options.reduce(function(acc, item){
    acc[item.value] = 1;
    return acc;
  }, {});

  // console.log(countTags(records, ['async'], clone(emptyCounts)));
  var selectize = $("#tags").selectize({
    options: options,
    create: true,
    score: getScoreFunction(records, emptyCounts),
    maxOptions: 10
  })[0].selectize;

  (function(records, emptyCounts){
    var oldSearch = selectize.search;
    selectize.search = function(){

      var res = oldSearch.apply(this, arguments);
      var tags = this.getValue().split(",");
      var counts = countTags(records, tags, clone(emptyCounts));

      console.log(counts);
      res.items = res.items.sort(function(a, b){
        // a.count =
        return counts[b.id] - counts[a.id];
      });

      console.log(Object.keys(counts).reduce(function(acc, item){
        if(counts[item] > 1){
          acc[item] = counts[item];
        }
        return acc;
      }, {}));
      console.log(res);

      delete res['options'];
      delete res['total'];
      return res;
    }
  })(records, emptyCounts);


  $("#submit").click(function(){
    options.forEach(function(item){
      item.text= 'ahaha';
    });
    selectize.refreshItems();
  });
});
