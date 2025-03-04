import React from "react";

const NewsSection = () => {
    const newsData = [
        {
            id: 1,
            title: "Marsz Niepodległości 2025",
            description: "Chodźmy razem na Marsz Niepodległości 2025, by uczcić naszą niepodległość i okazać jedność narodową.",
            author: "Redakcja",
            imagePath: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1RFSRk6VjNERIp7jDg42mkv0P320CxonPUQ&s",
        },
        {
            id: 2,
            title: "Nowa ustawa w Sejmie",
            description: "Sejm przyjął nową ustawę dotyczącą wsparcia dla przedsiębiorców w trudnych czasach.",
            author: "Jan Kowalski",
            imagePath: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1RFSRk6VjNERIp7jDg42mkv0P320CxonPUQ&s",
        },
        {
            id: 3,
            title: "Podsumowanie roku 2024",
            description: "Rok 2024 obfitował w wiele wydarzeń politycznych. Zobacz, co działo się w ciągu ostatnich 12 miesięcy.",
            author: "Anna Nowak",
            imagePath: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1RFSRk6VjNERIp7jDg42mkv0P320CxonPUQ&s",
        },
    ];

    return (
        <div className="bg-white shadow-xl rounded-lg p-6 mb-8">
            <h2 className="text-3xl font-bold text-gray-800 mb-6 text-center">Aktualności</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                {newsData.map((news) => (
                    <NewsCard
                        key={news.id}
                        date={"11 LIS"} // Możesz zaktualizować datę w zależności od potrzeb
                        title={news.title}
                        description={news.description}
                        image={news.imagePath}
                    />
                ))}
            </div>
        </div>
    );
};

const NewsCard = ({ date, title, description, image }) => (
    <div className="bg-gray-100 shadow-md rounded-lg overflow-hidden">
        <div className="relative">
            <img
                src={image}
                alt="News"
                className="object-cover w-full h-48"
            />
            <div className="absolute top-2 left-2 bg-blue-500 text-white text-sm font-bold px-3 py-1 rounded">
                {date}
            </div>
        </div>
        <div className="p-4">
            <h3 className="font-bold text-lg mb-2">{title}</h3>
            <p className="text-sm text-gray-600">
                {description}
            </p>
            <a
                href="#"
                className="text-blue-500 font-bold text-sm mt-2 inline-block hover:underline"
            >
                Czytaj więcej...
            </a>
        </div>
    </div>
);

export default NewsSection;
