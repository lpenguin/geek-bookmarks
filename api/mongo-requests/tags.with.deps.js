/* var uniq = function(a) {
    var seen = {};
    return a.filter(function(item) {
        return seen.hasOwnProperty(item) ? false : (seen[item] = true);
    });
}
*/

var map = function(){
    var tags = this.tags;
    tags.forEach(function(tag, i){
        tags.forEach(function(x, j){
            if(i != j){
                emit(tag, {name:x, count:1});
            }
        });
    });
}

var reduce = function(key, values){
    var counts = {};
    values.forEach(function(e){
        
        counts[e] = 1 + (counts[e] || 0);
    });
    return counts;
}
db.records.mapReduce(map, reduce, {out: { inline: 1 }})