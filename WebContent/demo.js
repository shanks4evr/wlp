app = angular.module("ccdbApp", ["ngRoute"]);
app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl: 'table_data.html',
			controller: 'ccdbController'
		})
		.otherwise({
			redirectTo: '/'
		});
});
app.controller('ccdbController', ["$scope", "$http", function($scope, $http) {
	$http.get("rest/data").then(function(response) {
		$scope.userData = response.data;
	});
}]);