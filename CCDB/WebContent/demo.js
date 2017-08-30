app = angular.module("ccdbApp", ["ngRoute", "ui.grid", "ui.grid.pagination", "ui.grid.resizeColumns"]);

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

app.controller('ccdbController', [
	'$scope', '$http', 'uiGridConstants', function($scope, $http, uiGridConstants) {
		var paginationOptions = {
			pageNumber: 1,
			pageSize: 25,
			sort: [],
			search: {author: null, title: null, primary_community: null}
		};
		
		$scope.gridOptions = {
			paginationPageSizes: [25, 50, 75],
			paginationPageSize: 25,
			useExternalPagination: true,
			useExternalSorting: true,
			enableGridMenu: true,
			enableColumnResizing: true,
			enableFiltering: true,
			useExternalFiltering: true,
			columnDefs: [
				{name: 'author', field: 'author'},
				{name: 'title', field: 'title'},
				{name: 'primary_community', field: 'primary_community'},
				{name: 'brand', field: 'brand', enableFiltering: false},
				{name: 'imts', field: 'imts', enableFiltering: false},
				{name: 'total_click', field: 'total_click', enableFiltering: false},
				{name: 'total_opens', field: 'total_opens', enableFiltering: false},
				{name: 'total_ocr', field: 'total_ocr', enableFiltering: false},
				{name: 'total_ccr', field: 'total_ccr', enableFiltering: false},
				{name: 'total_emails_sent', field: 'total_emails_sent', enableFiltering: false}
			],
			onRegisterApi: function(gridApi) {
				$scope.gridApi = gridApi;
				$scope.gridApi.core.on.filterChanged($scope, function() {
					var grid = this.grid;
					var author = grid.columns[0].filters[0].term;
					var title = grid.columns[1].filters[0].term;
					var primary_community = grid.columns[2].filters[0].term;
					
					if(author === "") {
						paginationOptions.search.author = null;
					} else if((author!==undefined) && (author!==null)) {
						paginationOptions.search.author = author;
					}
					
					if(title === "") {
						paginationOptions.search.title = null;
					} else if((title!==undefined) && (title!==null)) {
						paginationOptions.search.title = title;
					}
					
					if(primary_community === "") {
						paginationOptions.search.primary_community = null;
					} else if((primary_community!==undefined) && (primary_community!==null)) {
						paginationOptions.search.primary_community = primary_community;
					}
					
					paginationOptions.pageNumber = 1;
					getTotalItems();
					getNextPage();
				});
				$scope.gridApi.core.on.sortChanged($scope, function(grid, sortColumns) {
				    paginationOptions.sort = [];
				    sortColumns.forEach(function(column) {
				    	var sortColumn = {};
				    	sortColumn.column = column.name;
				    	sortColumn.priority = column.sort.priority;
				    	sortColumn.direction = column.sort.direction;
				    	paginationOptions.sort.push(sortColumn);
				    });
				    $scope.loading = true;
				    getNextPage();
				});
				gridApi.pagination.on.paginationChanged($scope, function(newPage, pageSize) {
					paginationOptions.pageNumber = newPage;
					paginationOptions.pageSize = pageSize;
					$scope.loading = true;
					getNextPage();
				});
			}
		};
		
		$scope.loading = true;
		//////////////////////////////////////
		
		var getNextPage = function() {
			var sortUrl = "";
			paginationOptions.sort.forEach(function(sortObj) {
				sortUrl = sortUrl + '&sortParams=' + JSON.stringify(sortObj);
			});
			searchUrl = "";
			if(paginationOptions.search.author!==null) {
				searchUrl = searchUrl + '&author=' + paginationOptions.search.author;
			}
			if(paginationOptions.search.title!==null) {
				searchUrl = searchUrl + '&title=' + paginationOptions.search.title;
			}
			if(paginationOptions.search.primary_community!==null) {
				searchUrl = searchUrl + '&primary_community=' + paginationOptions.search.primary_community;
			}
			var url = 'rest/data/page?pageNumber=' + paginationOptions.pageNumber + '&pageSize=' + paginationOptions.pageSize + sortUrl +searchUrl;
			$http.get(url).success(function(response) {
				$scope.gridOptions.data = response;
			}).finally(function() {
				$scope.loading = false;
			});
		};
		
		//////////////////////////////////////
		
		var getTotalItems = function() {
			var url = "rest/data/total?";
			if(paginationOptions.search.author!==null) {
				url = url + '&author=' + paginationOptions.search.author;
			}
			if(paginationOptions.search.title!==null) {
				url = url + '&title=' + paginationOptions.search.title;
			}
			if(paginationOptions.search.primary_community!==null) {
				url = url + '&primary_community=' + paginationOptions.search.primary_community;
			}
			$http.get(url).success(function(response) {
				$scope.gridOptions.totalItems = response;
			});
		};
		
		getTotalItems();
		getNextPage();
		
		///////////////////////////////////////
	}
]);