import { Button } from "@/components/ui/button";
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useState } from "react";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue
} from "@radix-ui/react-select";

interface FormCategoryData {
    name: string;
    status: string;
    parent_id: number;
}

export function DialogCreateOrUpdate({ buttonName, title }): JSX.Element {
    const [data, setData] = useState<FormCategoryData>({
        name: "",
        status: "",
        parent_id: 0
    });

    const handleStatusChange = (value: string) => {
        setData((prevData) => ({
            ...prevData,
            status: value
        }));
    };

    return (
        <Dialog>
            <DialogTrigger asChild>
                <Button variant="outline">{buttonName}</Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px] p-6 space-y-6">
                <DialogHeader>
                    <DialogTitle className="text-lg font-semibold">{title}</DialogTitle>
                    <DialogDescription className="text-sm text-gray-500">
                        Please fill out the information below.
                    </DialogDescription>
                </DialogHeader>
                <div className="grid gap-4 py-2">
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Label htmlFor="name" className="text-right text-gray-700">
                            Name
                        </Label>
                        <Input
                            id="name"
                            value={data.name}
                            onChange={(e) =>
                                setData((prevData) => ({
                                    ...prevData,
                                    name: e.target.value
                                }))
                            }
                            className="col-span-3 border-gray-300 rounded-md"
                        />
                    </div>
                    <div className="grid grid-cols-4 items-center gap-4">
                        <Label htmlFor="status" className="text-right text-gray-700">
                            Status
                        </Label>
                        <div className="col-span-3 border-amber-950 rounded-md">
                            <Select value={data.status} onValueChange={handleStatusChange}>
                                <SelectTrigger className="w-full border-gray-300 rounded-md">
                                    <SelectValue placeholder="Select" />
                                </SelectTrigger >
                                <SelectContent >
                                    <SelectItem value="publish">Publish</SelectItem>
                                    <SelectItem value="draft">Draft</SelectItem>
                                </SelectContent>
                            </Select>
                        </div>
                    </div>
                </div>
                <DialogFooter className="flex justify-end pt-4">
                    <Button type="submit" className="bg-black text-white">
                        Save changes
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
}
