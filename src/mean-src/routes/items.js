var express = require('express');
var router = express.Router();
var mongoose=require('mongoose');
mongoose.model('items', {});

router.get('/', function(req, res) {
	mongoose.model('items').find(function(err, items){
		res.json(items);
	});
});
router.get('/:cat', function(req, res){
	var cat = req.params.cat;
	mongoose.model('items').find({cat:cat}, function(err, items){
		res.json(items);
	});
});
router.get('/:cat/:item', function(req, res){
	var item = req.params.item;
	var cat = req.params.cat;
	console.log(cat + " " +item);
	mongoose.model('items').find({ cat:cat , uid:item }, function(err, item){
		res.json(item);
	});
});
router.get('/get/all/cats', function(req, res){
	mongoose.model('items').find().distinct('cat', function(err, cats){
		res.json(cats);
	});
});
module.exports = router;