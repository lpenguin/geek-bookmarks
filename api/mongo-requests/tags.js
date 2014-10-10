var map = function(){
    this.tags.map(function(tag, index){
        emit(tag, 1);
    });
}

var reduce = function(tag, values){
    return Array.sum(values);
}

db.records.mapReduce(map, reduce, {out: { inline: 1 }})