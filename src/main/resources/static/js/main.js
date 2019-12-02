function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

var cityApi = Vue.resource('/city{/id}');

Vue.component('city-form', {
    props: ['cities', 'cityAttr'],
    data: function () {
        return{
            text: '',
            goodplaces: '',
            badplaces: '',
            id: ''
        }
    },
    watch: {
        cityAttr:function(newVal, oldVal){
            this.text = newVal.name;
            this.goodplaces = newVal.goodplaces;
            this.badplaces = newVal.badplaces;
            this.id = newVal.id;
        }
    },
    template:
        '<div>'+
        'Город <input type="text" placeholder="Write something" v-model="text"/>' +
        '<br>Хорошие места <input type="good" placeholder="Write something" v-model="goodplaces"/>'+
        '<br>Плохие места <input type="text" placeholder="Write something" v-model="badplaces"/>'+
        '<input style="position: relative; width: 300px;" type="button" value="Save" @click="savecity"/>' +
        '<br><br></div>',
    methods: {
        savecity: function() {
            var city = { name: this.text, badplaces: this.badplaces, goodplaces: this.goodplaces };

            if(this.id) {
                cityApi.update({id: this.id}, city).then(result=>
                result.json().then(data => {
                    var index = getIndex(this.cities, data.id);
                this.cities.splice(index, 1,data);
            }
            ))
            }
            else{
                cityApi.save({}, city).then(result =>
                result.json().then(data => {
                    this.cities.push(data);
            })
            )
            }
            this.text = '';
            this.goodplaces = '';
            this.badplaces = '';
        }
    }
});

Vue.component('city-row', {
    props: ['city', 'editMethod', 'cities'],
    template: '<div><i>({{ city.id }})</i> Город {{city.name}} ' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="telegram" @click="telegram"/>' +
        '<input type="button" value="Edit" @click="currentedit"/>' +
        '<input type="button" value="X" @click="del"/>' +
        '</span>' +
        '<div>Хорошие места: {{city.goodplaces}} </div> ' +
        '<div>Плохие места: {{city.badplaces}} </div>'+
        '</div>',
    methods: {
        currentedit: function () {
            this.editMethod(this.city);
        },
        del: function () {
            cityApi.remove({id: this.city.id}).then(result => {
                if(result.ok){
                this.cities.splice(this.cities.indexOf(this.city), 1);
            }
        })
        },
        telegram: function () {
            cityApi.get({id: this.city.id}).then(result => {
                if(result.ok)
                         alert("information on the city of "+ this.city.name +  " was sent");
            });
            }
        }
    });

Vue.component('cities-list', {
    props: ['cities'],
    data: function(){
        return{
            city: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
        '<city-form :cities="cities" :cityAttr="city"/>'+
        '<city-row v-for="city in cities" :key="city.id" :city="city" ' +
        ':editMethod="editMethod" :cities="cities"/>' +
        '</div>',
    created: function() {
        cityApi.get().then(result =>
        result.json().then(data =>
        data.forEach(city => this.cities.push(city))
    )
    )
    },
    methods: {
        editMethod: function(city) {
            this.city = city;
        }
    }
});
var app = new Vue({
    el: '#app',
    template: '<cities-list :cities = "cities" />',
    data: {
        cities: []
    }
});