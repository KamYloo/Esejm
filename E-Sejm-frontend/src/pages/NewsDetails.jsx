import React, {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getNewsByIdAction} from "../redux/NewsService/Action.js";

const NewsDetails = () => {
    const dispatch = useDispatch();
    const {newsId} = useParams();
    const navigate = useNavigate();
    const {article} = useSelector(store => store.news);


    useEffect(() => {
        dispatch(getNewsByIdAction(newsId));
    }, [dispatch, newsId]);

    return (
        <div className="min-h-screen bg-gray-100 p-6">
            <div className="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow-md">
                <img
                    src={article?.imagePath}
                    alt={article?.title}
                    className="w-full h-60 object-cover rounded-lg mb-4"
                />


                <h1 className="text-4xl font-bold text-center mb-6">{article?.title}</h1>
                <div className="text-center mb-2 text-gray-600 italic">
                    Autor: {article?.author.firstName} {article?.author.lastName}
                </div>

                <div className="text-center mb-6">
                    <span
                        className={`inline-block px-3 py-1 rounded-full text-white ${
                            article?.category === "Polska"
                                ? "bg-red-500"
                                : "bg-blue-500"
                        }`}
                    >
                        {article?.category}
                    </span>
                </div>

                <div
                    className="text-lg text-gray-700 mb-6"
                    dangerouslySetInnerHTML={{__html: article?.content}}
                ></div>

                <div className="text-center">
                    <button
                        className="inline-block bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
                        onClick={() => navigate(-1)}
                    >
                        Powrót do wiadomości
                    </button>
                </div>
            </div>
        </div>
    );
};

export default NewsDetails;
