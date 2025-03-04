import React, {useEffect} from "react";
import {MainArticle} from "../features/news/MainArticle.jsx";
import {FinanceSection} from "../features/news/FinanceSection.jsx";
import {ArticleCard} from "../features/news/ArticleCard.jsx";
import {NewsSection} from "../features/news/NewsSection.jsx";
import {useDispatch, useSelector} from "react-redux";
import {getNewsListAction} from "../redux/NewsService/Action.js";
import {useNavigate} from "react-router-dom";

export function News() {
    const dispatch = useDispatch();
    const {article, newsList} = useSelector(store => store.news);
    const navigate = useNavigate();

    useEffect(() => {
        dispatch(getNewsListAction())
    }, [dispatch, article]);


    const sortedNews = [...newsList].sort((a, b) => b.id - a.id);
    const mainArticle = sortedNews[0];
    const lastArticles = sortedNews.slice(1, 4);
    const worldNews = sortedNews.filter(news =>
        news?.categories?.some(category => category.name.toLowerCase() === "swiat")
    );

    const polandNews = sortedNews.filter(news =>
        news?.categories?.some(category => category.name.toLowerCase() === "polska")
    );


    return (
        <div className="max-w-7xl mx-auto p-6 space-y-8 mt-8">
            <header className="text-5xl font-bold flex items-center space-x-4">
                <h2 className="text-red-600">Dzisiejsze</h2>Wiadomości
                <div className="flex-grow border-t-8 border-lineColor"></div>
            </header>

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                <div className="col-span-2">
                    <MainArticle article={mainArticle}/>
                </div>

                <div className="space-y-4">
                    <h2 className="text-lg font-semibold">Finanse</h2>
                    <FinanceSection/>
                </div>
            </div>

            <div>
                <h2 className="text-2xl font-semibold mb-4">Ostatnie Artykuły</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {lastArticles.map(article => (
                        <ArticleCard
                            key={article.id}
                            title={article.title}
                            description={article.content}
                            author={article.author}
                            image={article.imagePath}
                            onClick={() => navigate(`${article.id}`)}
                        />
                    ))}
                </div>
            </div>

            <div className="relative mt-10">
                <img
                    src="https://elements-resized.envatousercontent.com/elements-video-cover-images/files/c580bccd-8114-4c08-97c0-4be2b540a9c7/inline_image_preview.jpg?w=1200&h=630&cf_fit=crop&q=85&format=jpeg&s=1e6d2aacd4afbbd95e8f8a5769352c4a61944369a2a5bc2306a46c8d27946453"
                    alt="News Banner"
                    className="w-full h-60 object-cover rounded-md shadow-lg"
                />
                <div className="absolute inset-0 flex items-center justify-center">
                    <h3 className="text-white text-7xl font-bold drop-shadow-lg">Wiadomości</h3>
                </div>
            </div>

            <div className="mt-10">
                <div className="flex items-center space-x-4">
                    <h1 className="text-4xl font-bold text-black">Świat</h1>
                    <div className="flex-grow border-t-8 border-lineColor"></div>
                </div>
                <NewsSection newsData={worldNews} />
            </div>

            <div className="mt-10">
                <div className="flex items-center space-x-4">
                    <h1 className="text-4xl font-bold text-black">Polska</h1>
                    <div className="flex-grow border-t-8 border-lineColor"></div>
                </div>
                <NewsSection newsData={polandNews}/>
            </div>

        </div>
    )
}

