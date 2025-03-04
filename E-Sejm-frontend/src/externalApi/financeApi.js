export const fetchCurrencyData = async () => {
    try {
        const response = await fetch('https://api.nbp.pl/api/exchangerates/tables/A/?format=json');
        const data = await response.json();
        const rates = data[0].rates;

        const currencyList = [
            { name: 'Euro', symbol: 'EUR', price: `${rates.find(rate => rate.code === 'EUR').mid.toFixed(2)} PLN`, trend: 'up' },
            { name: 'US Dollar', symbol: 'USD', price: `${rates.find(rate => rate.code === 'USD').mid.toFixed(2)} PLN`, trend: 'up' },
            { name: 'Frank Szwajcarski', symbol: 'CHF', price: `${rates.find(rate => rate.code === 'CHF').mid.toFixed(2)} PLN`, trend: 'down' },
            { name: 'Funt Brytyjski', symbol: 'GBP', price: `${rates.find(rate => rate.code === 'GBP').mid.toFixed(2)} PLN`, trend: 'up' },
        ];

        return currencyList;
    } catch (error) {
        console.error('Error fetching currency data:', error);
        throw error;
    }
};

