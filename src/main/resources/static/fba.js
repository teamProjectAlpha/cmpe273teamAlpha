var app = angular.module('myApp', ['akoenig.deckgrid', 'ui.bootstrap']);
var photos = [
	{id: 'photo-1', src: 'http://lorempixel.com/300/400'},
	{id: 'photo-2', src: 'http://lorempixel.com/300/400'},
	{id: 'photo-3', src: 'http://lorempixel.com/300/400'},
	{id: 'photo-4', src: 'http://lorempixel.com/300/400'},
	{id: 'photo-5', src: 'http://lorempixel.com/300/400'},
	{id: 'photo-6', src: 'http://lorempixel.com/300/400'},
	{id: 'photo-7', src: 'http://lorempixel.com/300/400'}
];
app.controller('myCtrl', ['$scope', '$http', function ($scope, $http) {

	$scope.dispVal = 'true';
	$scope.detailDispVal = 'true';
	$scope.showModal = 'true';

	$scope.getAlbums = function () {
		$scope.dispVal = !$scope.dispVal;
		$http({
			method: 'GET',
			url: '/getalbums'
		}).success(function (response) {
			$scope.albums = response;
			return response;
		})
	};

	$scope.getPhotos = function (val) {
		$scope.album_id = val;
		$http({
			method: 'GET',
			url: '/' + $scope.album_id + '/photos'
		}).success(function (response) {
			$scope.photos = response;

		});
	};

	$scope.backupAlbum = function (val) {
		alert("Started backup... This will take few seconds to complete...Click OK to continue.");
		$scope.album_id = val;
		document.getElementById("linkbtn").disabled = true;
		$http({
			method: 'GET',
			url: '/backup?album_id=' + $scope.album_id
		}).success(function (response) {
			$scope.album_id = response;
			if ($scope.album_id !== null) {
				alert("Album " + $scope.album_id + " is successfully backed up!");
			} else {
				alert("Sorry, Backup failed");
			}
			document.getElementById("linkbtn").disabled = false;

		});
	};

	$scope.getAlbumMeta = function (val) {
		$scope.album_id = val;
		$scope.detailDispVal = !$scope.detailDispVal;
		$scope.isBackedUp = 'false';

		$http({
			method: 'GET',
			url: '/getalbummeta?album_id=' + $scope.album_id
		}).success(function (album) {
			$scope.mongoAlbum = album;
		});

		if ($scope.isBackedUp !== null) {

		} else {
			alert("Album not found on MongoDB!");

		}
	};


	$scope.showPhotos = function (val) {

		$scope.showModal = !$scope.showModal;
		$scope.album_id = val;
		$scope.isBackedUp = 'false';

		$http({
			method: 'GET',
			url: '/getalbummeta?album_id=' + $scope.album_id

		}).success(function (response) {
			$scope.status = response;
			if (status !== null) {
				$scope.isBackedUp = 'true';
			}

		});

		if ($scope.isBackedUp) {
			$http({
				method: 'GET',
				url: '/' + $scope.album_id + '/photos'
			}).success(function (photos) {

				$scope.photos = photos;

			});
		} else {
			alert('Please backup this album first!');
		}
	}

}]);