"use client";

import { useCallback, useEffect, useState } from "react";
import { useToast } from "../../contexts/ToastContext";
import useSWR from "swr";
import axiosInstance from "@/configs/axios";
import { RootState } from "@/redux/store";
import { useSelector } from "react-redux";
import { DataTable } from "@/components/DataTable/data-table";
import { Badge } from "@/components/ui/badge";

export default function ListUser() {
    const [pageIndex, setPageIndex] = useState(0);
    const { notify } = useToast();
    const userPerPage = 10;

    interface Product {
        id: string;
        name: string;
        description: string;
        richDescription: string;
        status: "In Stock" | "Out of Stock" | "Low Stock";
        category: string;
        thumbnail: File | null;
        gallery: File[];
        price: string;
    }
    const initialProducts: Product[] = [
        {
            id: "1",
            name: "Premium Laptop",
            description: "High-performance laptop for professionals",
            richDescription: `
        <h2>Premium Laptop Specifications</h2>
        <p>This high-performance laptop is designed for professionals who demand the best. It comes with the following features:</p>
        <ul>
          <li>Latest generation Intel Core i9 processor</li>
          <li>32GB DDR4 RAM</li>
          <li>1TB NVMe SSD</li>
          <li>NVIDIA RTX 3080 Graphics Card</li>
          <li>15.6" 4K OLED Display</li>
          <li>Thunderbolt 4 ports</li>
          <li>Wi-Fi 6E and Bluetooth 5.2</li>
          <li>Backlit keyboard with numpad</li>
          <li>Large precision touchpad</li>
          <li>HD webcam with privacy shutter</li>
          <li>All-day battery life</li>
          <li>Fingerprint reader and facial recognition</li>
          <li>Premium aluminum chassis</li>
          <li>Advanced cooling system</li>
          <li>Pre-installed productivity software suite</li>
        </ul>
        <p>This laptop is perfect for:</p>
        <ul>
          <li>Video editing and 3D rendering</li>
          <li>Software development</li>
          <li>Data analysis and visualization</li>
          <li>Virtual machine hosting</li>
          <li>High-end gaming</li>
        </ul>
        <p>With its powerful specifications and versatile features, this premium laptop is the ultimate tool for professionals who need uncompromising performance in a portable package.</p>
      `,
            status: "In Stock",
            category: "Electronics",
            thumbnail: null,
            gallery: [],
            price: "1299.99",
        },
    ];

    const columns = [
        { key: "name", title: "Name" },
        { key: "description", title: "Description" },
        {
            key: "status",
            title: "Status",
            render: (value: Product["status"]) => (
                <Badge
                    variant={
                        value === "In Stock"
                            ? "success"
                            : value === "Low Stock"
                            ? "warning"
                            : "destructive"
                    }
                >
                    {value}
                </Badge>
            ),
        },
        { key: "category", title: "Category" },
        { key: "price", title: "Price" },
    ];
    const handlePageChange = useCallback((newPage: number) => {
        setPageIndex(newPage);
    }, []);
    const token = useSelector((state: RootState) => state.auth.accessToken);
    const fetcher = (url: string, token: string) =>
        axiosInstance.get<any>(url, {
            headers: {
                Authorization: token ? `Bearer ${token}` : "",
            },
        });

    const {
        data: dataUserList,
        error,
        mutate,
    } = useSWR(
        token
            ? [`/api/v1/products?limit=${userPerPage}&page=${pageIndex}`, token]
            : null,
        ([url, token]) => fetcher(url, token)
    );

    const data =
        dataUserList?.data?.info !== undefined
            ? dataUserList?.data?.info.items.map((data: any) => ({
                  id: data.id,
                  name: data.name,
                  parent_id: data.parent_id,
                  status: data.status,
                  activity: new Date(data.created_at).toLocaleDateString(
                      "en-GB"
                  ),
              }))
            : null;

    const formFields = [
        { key: "name", label: "Name", type: "text" as const },
        {
            key: "description",
            label: "Short Description",
            type: "text" as const,
        },
        {
            key: "richDescription",
            label: "Detailed Description",
            type: "richText" as const,
        },
        {
            key: "status",
            label: "Status",
            type: "select" as const,
            options: [
                { value: "PUBLISH", label: "Publish" },
                { value: "DRAFT", label: "Draft" },
            ],
        },
        { key: "category", label: "Category", type: "text" as const },
        { key: "price", label: "Price", type: "text" as const },
        {
            key: "thumbnail",
            label: "Product Thumbnail",
            type: "image" as const,
        },
        {
            key: "gallery",
            label: "Product Gallery",
            type: "multipleImages" as const,
        },
    ];
    const handleDelete = async (id: number) => {
        try {
            await axiosApi.delete(`users/${id}`);
            mutate();
            toast.success("사용자를 삭제했습니다.");
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    };

    const handleSaveOrUpdate = async (data: Partial<Product>) => {
        console.log(data);

        if (data.id) {
            const response = await axiosInstance.put(
                `/api/v1/products/categories/${data.id}`,
                data,
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: token ? `Bearer ${token}` : "",
                    },
                }
            );

            mutate();
            notify(response.data.message || "Login successful!", {
                type: "success",
            });
        } else {
            try {
                const response = await axiosInstance.post(
                    `/api/v1/products/categories`,
                    data,
                    {
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: token ? `Bearer ${token}` : "",
                        },
                    }
                );

                mutate();
                notify(response.data.message || "Login successful!", {
                    type: "success",
                });
            } catch (error) {
                console.error("Error updating data:", error);
            }
        }
    };

    return (
        <DataTable
            data={data ?? initialProducts}
            columns={columns}
            title="Page List Product"
            subtitle="Manage List Product"
            formFields={formFields}
            onDelete={handleDelete}
            onSave={handleSaveOrUpdate}
        />
    );
}
