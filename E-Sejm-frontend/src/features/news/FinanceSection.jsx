import React, {useEffect, useState} from 'react';
import {fetchCurrencyData} from "../../externalApi/financeApi.js";

const FinanceSection = () => {
    const [currencyData, setCurrencyData] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const getData = async () => {
            try {
                const data = await fetchCurrencyData();
                setCurrencyData(data);
                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch data:', error);
                setLoading(false);
            }
        };

        getData();
    }, []);

    return (
        <div className="bg-gray-50 p-4 rounded-md shadow-md">
            {loading ? (
                <div className="text-center text-gray-500">Loading...</div>
            ) : (
                <ul className="space-y-3">
                    {currencyData.map((item, index) => (
                        <li key={index} className="flex justify-between items-center">
                            <div className="flex items-center space-x-3">
                                <span className="text-lg font-semibold">{item.name}</span>
                                <span className="text-gray-500 text-sm">{item.symbol}</span>
                            </div>
                            <div className={`text-lg ${item.trend === 'up' ? 'text-green-600' : 'text-red-600'}`}>
                                {item.price} {item.trend === 'up' ? '↑' : '↓'}
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export { FinanceSection };
